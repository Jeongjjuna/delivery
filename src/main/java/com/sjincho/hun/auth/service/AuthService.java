package com.sjincho.hun.auth.service;

import com.sjincho.hun.auth.dto.LoginRequest;
import com.sjincho.hun.auth.exception.AuthErrorCode;
import com.sjincho.hun.auth.exception.PasswordInvalidException;
import com.sjincho.hun.config.filter.JwtTokenProvider;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.exception.MemberErrorCode;
import com.sjincho.hun.member.exception.MemberNotFoundException;
import com.sjincho.hun.member.service.port.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.accessToken.expired-time-ms}")
    private Long accessTokenExpiredTimeMs;

    public AuthService(final MemberRepository memberRepository, final BCryptPasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
    }

    public String jwtLogin(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.NOT_FOUND, email));

        checkPassword(password, member);

        return JwtTokenProvider.generate(member.getName(), member.getEmail(), secretKey, accessTokenExpiredTimeMs);
    }

    private void checkPassword(String password, Member member) {
        if (!encoder.matches(password, member.getPassword())) {
            throw new PasswordInvalidException(AuthErrorCode.INVALID_PASSWORD);
        }
    }
}
