package com.hp1.friendmatchingapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Spring 서버 전역적으로 CORS 설정
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // “*“같은 와일드카드를 사용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP method
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .allowedHeaders("*");
    }
}


//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOriginPattern("*"); // 모든 도메인 허용
//        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
//        config.addAllowedHeader("*"); // 모든 헤더 허용
//        config.setAllowCredentials(true); // 인증 정보 허용
//
//        // Preflight 요청에 대한 설정
//        config.addExposedHeader("Authorization"); // 필요시 노출할 헤더 추가
//        config.setMaxAge(3600L); // Preflight 요청 캐싱 시간 설정 (1시간)
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        return new CorsFilter(source);
//    }
//}
