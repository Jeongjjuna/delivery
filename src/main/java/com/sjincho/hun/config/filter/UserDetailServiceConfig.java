package com.sjincho.hun.config.filter;

import com.sjincho.hun.member.dto.MemberResponse;
import com.sjincho.hun.member.service.port.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(MemberRepository memberRepository) {
        return userEmail -> memberRepository.findByEmail(userEmail)
                .map(MemberResponse::from)
                .map(User::from)
                .orElseThrow(() -> new UsernameNotFoundException("[ERROR] User Not Found"));
    }
}
