package com.example.eureka_gateway.eureka_gateway.rest;

import com.example.eureka_gateway.eureka_gateway.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public Mono<String> authenticate(@RequestBody Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }
}
