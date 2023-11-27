package com.sjincho.hun.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String secretKey;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(final String secretKey, final UserDetailsService userDetailsService) {
        this.secretKey = secretKey;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info(header);
        if (header == null || !header.startsWith("Bearer ")) {
            log.error("jwt 헤더값이 null 이거나 존재하지 않습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails user;
        try {
            // 1. header jwt토큰의 유효기간을 확인한다.
            final String token = header.split(" ")[1].trim();
            if (JwtTokenProvider.isExpired(token, secretKey)) {
                log.error("토근 유효기간이 만료되었습니다.");
                throw new IllegalArgumentException("[임시] 유효하지 않은 토큰 에러");
            }

            // 2. header jwt토큰의 사용자 email이 존재하는지 확인한다.
            String name = JwtTokenProvider.getUserName(token, secretKey);
            String email = JwtTokenProvider.getEmail(token, secretKey);

            // ??? : UserDetailsService를 꼭써야하는가? 곧바로 MemberRepository를 사용하면 안되는가?
            user = userDetailsService.loadUserByUsername(email);

            // 3. UserDetails User객정보를 시큐리티 컨텍스트 홀더에 넣어준다.
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication); //-> 컨트롤러로 Authentiaction을 보내준다.

        }catch (RuntimeException e) {
            log.error("Error occurs while validating. {}", e.toString());
            filterChain.doFilter(request, response);
            return;
        }

        log.info(user.getUsername() + " : 로그인 인증에 성공.");
        filterChain.doFilter(request, response);
    }
}
