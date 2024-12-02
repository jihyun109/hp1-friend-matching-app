package com.hp1.friendmatchingapp.service;

import com.hp1.friendmatchingapp.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public interface UserService {
    void addUser(UserCreateRequestDTO userCreateRequest);
    void auth(AuthUserAuthRequestDTO userAuthRequestDTO);
    Long authenticateUser(LoginRequestDTO loginRequestDTO);

    Slice<UserScrollListResponseDTO> getInfiniteScrollUsers(int pageNumber, int pageSize);

    void sendCodeToEmail(EmailVerificationRequestDto requestDto);
    String createCode();
    void verifiedCode(String requestDto, String authCode);
    Page<UserMatchingResponseDto> getMatchedUsersForPage(UserMatchingRequestDto userMatchingRequest);
    void updateProfileImage(Long userId, String imageUrl);
}
