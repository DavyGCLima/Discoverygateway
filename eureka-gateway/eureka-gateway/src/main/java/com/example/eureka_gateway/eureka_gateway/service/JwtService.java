package com.example.eureka_gateway.eureka_gateway.service;

import com.example.eureka_gateway.eureka_gateway.config.JHipsterProperties;
import com.example.eureka_gateway.eureka_gateway.config.JWTProperties;
import com.example.eureka_gateway.eureka_gateway.repository.AuthorizationRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final long tokenValidityInMilliseconds;
    private final JWTProperties jwtProperties;
    private final JHipsterProperties jHipsterProperties;
    private final AuthorizationRepository autorizacaoCache;

    public String createToken(Authentication authentication) {

        long now = (new Date()).getTime();
        var validity = new Date(now + this.tokenValidityInMilliseconds);
        return buildToken(
                        authentication,
                        Jwts.builder()
                                .signWith(getKey(), SignatureAlgorithm.HS512)
                                .setExpiration(validity)
                        , jwtProperties.getAuthorizationGroup())
                .compact();
    }

    public Key getKey() {
        String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public JwtBuilder buildToken(Authentication authentication, JwtBuilder builder, String issuer) {
        String subject = authentication.getName();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        autorizacaoCache.put(issuer + subject, authentication);
        return builder
                .setSubject(subject)
                .setIssuer(issuer);
    }
}
