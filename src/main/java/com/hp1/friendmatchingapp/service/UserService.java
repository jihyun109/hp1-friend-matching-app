package com.hp1.friendmatchingapp.service;

import com.hp1.friendmatchingapp.dto.*;
import com.hp1.friendmatchingapp.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public interface UserService {
    void addUser(UserCreateRequestDTO userCreateRequest);
    void auth(AuthUserAuthRequestDTO userAuthRequestDTO);
    UserEntity authenticateUser(LoginRequestDTO loginRequestDTO);

    Slice<UserScrollListResponseDTO> getInfiniteScrollUsers(int pageNumber, int pageSize);

    void sendCodeToEmail(EmailVerificationRequestDto requestDto);
    String createCode();
    void verifiedCode(String requestDto, String authCode);
    Page<UserMatchingResponseDto> getMatchedUsersForScroll(UserMatchingRequestDto userMatchingRequest);
}
