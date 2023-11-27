package com.sjincho.hun.config.filter;

import com.sjincho.hun.auth.exception.AuthErrorCode;
import com.sjincho.hun.auth.exception.AuthorizationHeaderNullException;
import com.sjincho.hun.auth.exception.NotBearerAuthorizationException;
import com.sjincho.hun.auth.exception.TokenExpiredException;
import com.sjincho.hun.auth.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(final UserDetailsService userDetailsService, final JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {

        try {
            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            checkNull(authorizationHeader);
            checkBearer(authorizationHeader);

            final String token = parseBearerToken(authorizationHeader);

            checkTokenExpired(token);

            String email = jwtTokenProvider.getEmail(token);
            UserDetails user = userDetailsService.loadUserByUsername(email);

            AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (AuthorizationHeaderNullException e) {
            request.setAttribute("exception", e);
        } catch (NotBearerAuthorizationException e) {
            request.setAttribute("exception", e);
        } catch (TokenExpiredException e) {
            request.setAttribute("exception", e);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void checkTokenExpired(final String jwtToken) {
        if (jwtTokenProvider.isExpired(jwtToken)) {
            throw new TokenExpiredException(AuthErrorCode.TOKEN_EXPIRED);
        }
    }

    private void checkNull(final String header) {
        try {
            Objects.requireNonNull(header);
        } catch (NullPointerException e) {
            throw new AuthorizationHeaderNullException(AuthErrorCode.AUTHORIZATION_HEADER_NULL);
        }
    }

    private void checkBearer(final String authorizationHeader) {
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new NotBearerAuthorizationException(AuthErrorCode.INVALID_BEARER);
        }
    }

    private String parseBearerToken(final String header) {
        return header.split(" ")[1].trim();
    }

}
