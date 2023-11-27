package com.sjincho.hun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        // POST /memebers외에 모두 인증을 거쳐야 한다.
        // 1. 요청받은 id, 비밀번호를 검사한다.(혹은로그인정보)
        // 2. 인증된 정보를 SecurityContextHolder 안에 값으로 넣어준다.(값이 있기만 하면 인증된 것으로 간주한다)
        http.authorizeHttpRequests(auth -> auth
//                .requestMatchers(HttpMethod.POST, "/members").permitAll()
                        .anyRequest().permitAll()
        );

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}