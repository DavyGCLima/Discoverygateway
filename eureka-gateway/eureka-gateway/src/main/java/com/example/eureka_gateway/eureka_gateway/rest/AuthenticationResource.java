package com.example.eureka_gateway.eureka_gateway.rest;

import com.example.eureka_gateway.eureka_gateway.DTO.TokenDetailsDTO;
import com.example.eureka_gateway.eureka_gateway.domain.Login;
import com.example.eureka_gateway.eureka_gateway.service.AuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public Mono<TokenDetailsDTO> authenticate(@RequestBody Login authentication) {
        return authenticationService.authenticate(new UsernamePasswordAuthenticationToken(authentication.getUserName(), authentication.getPassword()));
    }
}
