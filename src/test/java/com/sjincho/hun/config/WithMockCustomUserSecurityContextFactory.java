package com.sjincho.hun.config;

import com.sjincho.hun.auth.service.UserService;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.service.port.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import java.lang.annotation.Annotation;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<Annotation> {
    private final MemberRepository memberRepository;
    private final UserService userService;

    public WithMockCustomUserSecurityContextFactory(final MemberRepository memberRepository, final UserService userService) {
        this.memberRepository = memberRepository;
        this.userService = userService;
    }

    @Override
    public SecurityContext createSecurityContext(final Annotation annotation) {

        if (annotation instanceof OwnerMockUser mockUser) {
            Member member = Member.builder()
                    .name("anonymous")
                    .cellPhone("000-0000-0000")
                    .email(mockUser.email())
                    .password("pw")
                    .memberRole(mockUser.role())
                    .build();

            memberRepository.save(member);

            UserDetails user = userService.loadUserByUsername(mockUser.email());
            Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            return context;

        } else if (annotation instanceof CustomerMockUser mockUser) {
            Member member = Member.builder()
                    .name("anonymous")
                    .cellPhone("000-0000-0000")
                    .email(mockUser.email())
                    .password("pw")
                    .memberRole(mockUser.role())
                    .build();

            memberRepository.save(member);

            UserDetails user = userService.loadUserByUsername(mockUser.email());
            Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            return context;
        }

        return null;
    }
}
