package com.sjincho.hun.auth.service;

import com.sjincho.hun.auth.dto.User;
import com.sjincho.hun.member.service.port.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public UserService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String userEmail) throws UsernameNotFoundException {
        return memberRepository.findByEmail(userEmail)
                .map(User::from)
                .orElseThrow(() -> new UsernameNotFoundException("[ERROR] User Not Found"));
    }
}