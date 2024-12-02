package com.hp1.friendmatchingapp.service;

import com.hp1.friendmatchingapp.dto.*;
import com.hp1.friendmatchingapp.entity.HobbyEntity;
import com.hp1.friendmatchingapp.entity.UserEntity;
import com.hp1.friendmatchingapp.enums.Gender;
import com.hp1.friendmatchingapp.enums.Hobby;
import com.hp1.friendmatchingapp.error.customExceptions.*;
import com.hp1.friendmatchingapp.error.ErrorCode;
import com.hp1.friendmatchingapp.repository.HobbyRepository;
import com.hp1.friendmatchingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final RedisService redisService;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;
    @Value("${cloud.aws.default-profile-image-url}")
    private String defaultProfileImageUrl;

    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    @Override
    public void addUser(UserCreateRequestDTO userCreateRequest) {
        String username = userCreateRequest.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException("Duplicated username", ErrorCode.DUPLICATE_USER);
        }

        // 생년월일 Date로 변환
        LocalDate birthDate = convertToDate(userCreateRequest.getBirthDate());

        // 나이 추출
        int age = calculateAge(birthDate);
        int ageRange = (age / 10) * 10;

        // 취미 set 생성
        Set<HobbyEntity> hobbies = new HashSet<>();
        for (Hobby hobbyName : userCreateRequest.getHobbies()) {

            HobbyEntity hobby = hobbyRepository.findByHobbyName(hobbyName)
                    .orElseGet(() -> hobbyRepository.save(new HobbyEntity(hobbyName)));
            hobbies.add(hobby);
        }

        UserEntity user = UserEntity.builder()
                .username(userCreateRequest.getUsername())
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .chatRoomUrl(userCreateRequest.getChatRoomUrl())
                .firstName(userCreateRequest.getFirstName())
                .birthDate(birthDate)
                .profileImageUrl(defaultProfileImageUrl)
                .age(age)
                .ageRange(ageRange)
                .gender(userCreateRequest.getGender())
                .chatRoomUrl(userCreateRequest.getChatRoomUrl())
                .hobbies(hobbies)
                .build();

        userRepository.save(user);
    }


    @Override
    public void auth(AuthUserAuthRequestDTO userAuthRequestDTO) {
    }

    @Override
    public UserEntity authenticateUser(LoginRequestDTO loginRequestDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByUsername(loginRequestDTO.getUsername());

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            String password = userEntity.getPassword();

            if (passwordEncoder.matches(loginRequestDTO.getPassword(), userEntity.getPassword())) {
                return userEntity; // 인증 성공 시 사용자 반환
            } else {
                throw new InvalidPasswordException("Invalid password", ErrorCode.INVALID_PASSWORD);
            }
        } else {
            throw new UserNotFoundException("User not found with id: " + loginRequestDTO.getUsername(), ErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<UserScrollListResponseDTO> getInfiniteScrollUsers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Slice<UserEntity> userEntitySlice = userRepository.findSliceByOrderByIdAsc(pageable);

        List<UserScrollListResponseDTO> userRequests = mapToUserScrollListResponse(userEntitySlice.getContent());

        return new SliceImpl<>(userRequests, pageable, userEntitySlice.hasNext());
    }

    // email 인증 관련
    @Override
    public void sendCodeToEmail(EmailVerificationRequestDto requestDto) {
        String toEmail = requestDto.getEmail();
        this.checkDuplicatedEmail(toEmail);
        String title = "HP-1 Friend Matching Application 이메일 인증 번호";
        String authCode = this.createCode();
        mailService.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.setValues(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(this.authCodeExpirationMillis));
    }

    @Override
    public String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            throw new RuntimeException();
        }
    }

    @Override
    public void verifiedCode(String email, String authCode) {
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        if (!redisService.checkExistsKey(AUTH_CODE_PREFIX + email)) {
            throw new EmailVerificationRequestNotFoundException("No verification request found for the provided email.", ErrorCode.EMAIL_VERIFICATION_REQUEST_NOT_FOUND);
        } else if (!redisAuthCode.equals(authCode)) {
            throw new InvalidEmailVerificationCodeException(
                    ErrorCode.INVALID_EMAIL_VERIFICATION_CODE.getMessage(), ErrorCode.INVALID_EMAIL_VERIFICATION_CODE);
        }
    }

//    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    @Override
    public Page<UserMatchingResponseDto> getMatchedUsersForPage(UserMatchingRequestDto userMatchingRequestDto) {
        Pageable pageable = PageRequest.of(userMatchingRequestDto.getPageNum(), userMatchingRequestDto.getPageSize());
        Set<Gender> genders = userMatchingRequestDto.getGender();
        Set<Hobby> hobbies = userMatchingRequestDto.getHobbies();
        Set<Integer> ageRanges = userMatchingRequestDto.getAgeRanges();
        Long userId = userMatchingRequestDto.getUserId();
        Page<UserMatchingResponseDto> matchedUsers = userRepository.findUserEntitiesByByGenderAAndHobbiesExcludingSelf(userId, genders, hobbies,ageRanges, pageable);

        List<UserMatchingResponseDto> userMatchingResponseDtos = mapToUserMatchingResponseDto(matchedUsers.getContent());
        return new PageImpl<>(userMatchingResponseDtos, pageable, matchedUsers.getTotalElements());
    }

    private List<UserMatchingResponseDto> mapToUserMatchingResponseDto(List<UserMatchingResponseDto> userEntity) {
        return userEntity.stream()
                .map(this::convertToUserMatchingResponseDto)
                .collect(Collectors.toList());
    }

    private UserMatchingResponseDto convertToUserMatchingResponseDto(UserMatchingResponseDto userEntity) {
        return new UserMatchingResponseDto(
                userEntity.getId(),
                userEntity.getFirstname(),
                userEntity.getAge(),
                userEntity.getGender(),
                userEntity.getProfileImageUrl()
        );
    }

    private List<UserScrollListResponseDTO> mapToUserScrollListResponse(List<UserEntity> userEntity) {
        return userEntity.stream()
                .map(this::convertToUserScrollListResponseDTO)
                .collect(Collectors.toList());
    }

    private UserScrollListResponseDTO convertToUserScrollListResponseDTO(UserEntity userEntity) {
        return new UserScrollListResponseDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail()
        );
    }

    public void checkDuplicatedEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            log.warn("checkDuplicatedEmail exception occur email: {}", email);
            throw new DuplicateEmailException("This email is already registered", ErrorCode.DUPLICATE_EMAIL);
        }
    }

    private LocalDate convertToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(dateString, formatter);
    }

    private int calculateAge(LocalDate birthDate) {
        int currentYear = LocalDate.now().getYear();
        int birthYear = birthDate.getYear();
        return currentYear - birthYear + 1;
    }
}
