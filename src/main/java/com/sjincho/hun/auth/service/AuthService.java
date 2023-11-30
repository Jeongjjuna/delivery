package com.sjincho.hun.auth.service;

import com.sjincho.hun.auth.dto.LoginRequest;
import com.sjincho.hun.auth.exception.AuthErrorCode;
import com.sjincho.hun.auth.exception.PasswordInvalidException;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.exception.MemberErrorCode;
import com.sjincho.hun.member.exception.MemberNotFoundException;
import com.sjincho.hun.member.service.port.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder encoder;

    public AuthService(final MemberRepository memberRepository,
                       final JwtTokenProvider jwtTokenProvider,
                       final BCryptPasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.encoder = encoder;
    }

    public String jwtLogin(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.NOT_FOUND, email));

        if (isPasswordMatching(password, member)) {
            return jwtTokenProvider.generate(member.getName(), member.getEmail());
        }

        throw new PasswordInvalidException(AuthErrorCode.INVALID_PASSWORD);
    }

    private boolean isPasswordMatching(String inputPassword, Member member) {
        return encoder.matches(inputPassword, member.getPassword());
    }
}
