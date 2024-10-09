package com.example.eureka_gateway.eureka_gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("nuvem.seguranca.jwt")
@Data
public class JWTProperties {
    private String authorizationGroup = "";
    private String authoritiesKey = "auth";
    private String tokenCookieName = "jwt-token";
    private String tokenCookiePath = "/";
    private String bearer = "Bearer ";
    private String authorizationHeader = "Authorization";
    private Long expiration = 300l;
    private String oversizedCookieExceptionMessage = "O token excede o tamanho permitido para cookies HTTP: https://tools.ietf.org/html/rfc6265#page-27";

}
