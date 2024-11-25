package com.hp1.friendmatchingapp.jwt;

import com.hp1.friendmatchingapp.dto.AuthUserAuthRequestDTO;
import com.hp1.friendmatchingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        AuthUserAuthRequestDTO authUserAuthRequestDTO = new AuthUserAuthRequestDTO(username, password);

        userService.auth(authUserAuthRequestDTO); // AuthService에서 인증 처리
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
    }
}
