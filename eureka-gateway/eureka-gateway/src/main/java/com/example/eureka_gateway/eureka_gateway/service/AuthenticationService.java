package com.example.eureka_gateway.eureka_gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public String authenticate() {
        return "token";
    }

}
