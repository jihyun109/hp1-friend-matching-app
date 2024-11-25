package com.hp1.friendmatchingapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

//    private UserService userService;
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Bean
//    public InitialAuthenticationFilter initialAuthenticationFilter(AuthenticationManager authManager) {
//        return new InitialAuthenticationFilter(authManager);
//    }
//
//    @Bean
//    public UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider() {
//        return new UsernamePasswordAuthenticationProvider(userService);
//    }
//
//    @Bean
//    public AuthenticationManager authManager() {
//        // AuthenticationProvider 목록 설정
//        List<AuthenticationProvider> providers = Arrays.asList(
//                usernamePasswordAuthenticationProvider()
//        );
//        return new ProviderManager(providers);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signup", "/emails/**").permitAll()  // /login, /signup 경로는 인증 없이 접근 가능
                        .anyRequest().authenticated());  // 그 외 모든 요청은 인증된 사용자만 접근 가능

        return http.build();  // 필터 체인 빌드
    }
}

