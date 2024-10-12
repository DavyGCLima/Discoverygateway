package com.example.eureka_gateway.eureka_gateway.service;

import com.example.eureka_gateway.eureka_gateway.config.JHipsterProperties;
import com.example.eureka_gateway.eureka_gateway.config.JWTProperties;
import com.example.eureka_gateway.eureka_gateway.repository.AuthorizationRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JWTProperties jwtProperties;
    private final JHipsterProperties jHipsterProperties;
    private final AuthorizationRepository autorizacaoCache;

    public Mono<String> createToken(Authentication authentication) {

        long now = (new Date()).getTime();
        var validity = new Date(now + jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds());
        return Mono.just(buildToken(
                        authentication,
                        Jwts.builder()
                                .signWith(getKey(), SignatureAlgorithm.HS512)
                                .setExpiration(validity)
                        , jwtProperties.getAuthorizationGroup())
                .compact());
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

    public Optional<Jws<Claims>> validateToken(String authToken) {
        try {
            return Optional.ofNullable(Jwts.parser().verifyWith((SecretKey) getKey()).build().parseClaimsJws(authToken));
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
            return Optional.empty();
        }
    }

    public Authentication getAuthentication(Jws<Claims> jws, String token) {
        return getAuthenticationChain(jws, token);
    }

    public Authentication getAuthenticationChain(Jws<Claims> jws, String token) {
        Claims claims = jws.getBody();
        try {
            String key = claims.getIssuer() + claims.getSubject();
            Optional<UserDetails> userDetails = autorizacaoCache.get(key);
            if (userDetails.isPresent()) {
                UserDetails principal = userDetails.get();
                Optional<Authentication> authentication = buildAuthentication(token, principal, principal.getAuthorities());
                if (isExpired(jws) && authentication.isPresent()) {
                    autorizacaoCache.put(key, authentication.get());
                }
                return authentication.get();
            }
        } catch (RuntimeException ex) {
            log.warn("Erro ao acessar o cache de autorização do usuário logado.");
            log.debug("Erro ao acessar o cache de autorização do usuário logado.", ex);
        }
        return null;
    }

    public Optional<Authentication> buildAuthentication(
            String token, UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
        authentication.setDetails(authorities);

        return Optional.of(authentication);
    }

    public boolean isExpired(Jws<Claims> result) {
        Date exp = result.getBody().getExpiration();
        return exp != null && new Date(new Date().getTime() + getExpiration()).after(exp);
    }

    private Long getExpiration() {
        return jwtProperties.getExpiration() * 1000l;
    }

}
