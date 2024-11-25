package com.hp1.friendmatchingapp.controller;

import com.hp1.friendmatchingapp.dto.*;
import com.hp1.friendmatchingapp.entity.UserEntity;
import com.hp1.friendmatchingapp.jwt.JwtUtil;
import com.hp1.friendmatchingapp.repository.UserRepository;
import com.hp1.friendmatchingapp.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/users")
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @PostMapping("/signup")
    public ResponseEntity<String> addAuthUser(@RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        userService.addUser(userCreateRequestDTO);
        return ResponseEntity.ok("유저 생성 완료");
    }

    @PostMapping("/login")
    public LoginResponseDto login (@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        // 사용자 인증
        UserEntity user = userService.authenticateUser(loginRequestDTO);

        String username = loginRequestDTO.getUsername();

        // 인증 성공 시 JWT 토큰 생성 및 반환
        String token = JwtUtil.generateToken(username);
        response.setHeader("Authorization", "Bearer " + token);
        return new LoginResponseDto(token);
    }

    @PostMapping("/emails/verification-request")
    public ResponseEntity sendMessage(@RequestBody EmailVerificationRequestDto requestDto) {
        userService.sendCodeToEmail(requestDto);

        return ResponseEntity.ok("이메일 전송 완료");
    }

    @GetMapping("/emails/verifications")
    public ResponseEntity<String> verificationEmail(@RequestParam("email")String email, @RequestParam("code") String code) {
        userService.verifiedCode(email, code);

        return ResponseEntity.ok("이메일 인증 완료");
    }
}
