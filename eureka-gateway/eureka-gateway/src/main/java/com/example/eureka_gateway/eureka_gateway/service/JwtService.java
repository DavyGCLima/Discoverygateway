package com.example.eureka_gateway.eureka_gateway.service;

import com.example.eureka_gateway.eureka_gateway.config.JHipsterProperties;
import com.example.eureka_gateway.eureka_gateway.config.JWTProperties;
import com.example.eureka_gateway.eureka_gateway.repository.AuthorizationRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final long tokenValidityInMilliseconds;
    private final JWTProperties jwtProperties;
    private final JHipsterProperties jHipsterProperties;
    private final AuthorizationRepository autorizacaoCache;
    private final JwtEncoder encoder;

    public String createToken(Authentication authentication) {
        String subject = authentication.getName();
        var now = Instant.now();
        var validity = now.plusMillis(tokenValidityInMilliseconds);

        var claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getAuthorizationGroup())
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName());

        autorizacaoCache.put(jwtProperties.getAuthorizationGroup() + subject, authentication);
        return encoder.encode(JwtEncoderParameters.from(claims.build())).getTokenValue();
    }
}
