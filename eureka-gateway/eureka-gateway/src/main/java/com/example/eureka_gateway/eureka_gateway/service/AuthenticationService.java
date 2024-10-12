package com.example.eureka_gateway.eureka_gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;

    public Mono<String> authenticate(Authentication authentication) {
        return jwtService.createToken(authentication);
    }

}
