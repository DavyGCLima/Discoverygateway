package com.example.eureka_gateway.eureka_gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final TokenResolver tokenResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = tokenResolver.resolveToken(request, (HttpServletResponse) servletResponse);

        if (StringUtils.hasText(jwt)) {
            Optional<Jws<Claims>> result = this.tokenProvider.validateToken(jwt);
            result.ifPresent(claimsJws -> this.tokenProvider.authenticate(request, response, claimsJws, jwt));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
