package com.hp1.friendmatchingapp.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private String signingKey;

    public JwtUtil(@Value("${jwt.signing.key}") String signingKey) {
        this.signingKey = signingKey;
    }

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간 유효

    public String generateToken(String username) {
        SecretKey secretKey = new SecretKeySpec(signingKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());;
        return Jwts.builder()
                .setClaims(Map.of("userName", username))    // JWT에 사용자 이름 저장
                .setIssuedAt(new Date())  // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(secretKey) // 비밀 키로 서명
                .compact(); // JWT 생성
    }
}
