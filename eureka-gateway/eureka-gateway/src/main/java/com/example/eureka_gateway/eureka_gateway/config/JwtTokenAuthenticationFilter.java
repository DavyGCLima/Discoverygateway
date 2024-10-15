package com.example.eureka_gateway.eureka_gateway.config;

import com.example.eureka_gateway.eureka_gateway.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter implements WebFilter {

    public static final String HEADER_PREFIX = "Bearer ";

    private final JwtService tokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());
        var tokenValidated = this.tokenProvider.validateToken(token);
        if (StringUtils.hasText(token) && tokenValidated.isPresent()) {
            return Mono.fromCallable(() -> this.tokenProvider.getAuthentication(tokenValidated.get(), token))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(authentication -> chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                return tokenProvider.createToken(authentication).then();
            }
        }
        return chain.filter(exchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(HEADER_PREFIX.length());
        }
        return null;
    }
}
