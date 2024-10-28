package com.sfiss.gateway.gateway_mvc.security;

import com.sfiss.gateway.gateway_mvc.cache.AuthorizationRepository;
import com.sfiss.gateway.gateway_mvc.config.JHipsterProperties;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

    private final long tokenValidityInMilliseconds;

    private final AuthorizationRepository autorizacaoCache;

    private final Key key;

    public JwtTokenProvider(AuthorizationRepository autorizacaoCache, JHipsterProperties properties) {
        this.tokenValidityInMilliseconds = properties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.autorizacaoCache = autorizacaoCache;
        this.key = Keys.hmacShaKeyFor(
                properties.getSecurity().getAuthentication().getJwt().getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return buildToken(
                        authentication,
                        Jwts.builder()
                                .signWith(key, SignatureAlgorithm.HS512)
                                .setExpiration(validity))
                .compact();
    }

    public JwtBuilder buildToken(Authentication authentication, JwtBuilder builder) {
        String subject = authentication.getName();
        autorizacaoCache.put(subject, authentication);
        return builder
                .setSubject(subject)
                .setIssuer("");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key).build().parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
