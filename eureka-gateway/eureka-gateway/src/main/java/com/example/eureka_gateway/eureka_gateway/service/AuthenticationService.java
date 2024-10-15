package com.example.eureka_gateway.eureka_gateway.service;

import com.example.eureka_gateway.eureka_gateway.DTO.TokenDetailsDTO;
import com.example.eureka_gateway.eureka_gateway.repository.ResponsibleEntitiesRepository;
import com.example.eureka_gateway.eureka_gateway.repository.UsersResponsibleEntityRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final ResponsibleEntitiesRepository responsibleEntitiesRepository;
    private final UsersResponsibleEntityRepository usersResponsibleEntityRepository;

    public Mono<TokenDetailsDTO> authenticate(Authentication authentication) {
        return jwtService.createToken(authentication)
                .flatMap(token -> createTokenDetails(token, authentication));
    }

    private Mono<TokenDetailsDTO> createTokenDetails(String token, Authentication authentication) {
        return createDTO(token).map(tokenDetailsDTO -> {
            if(isCurrentUserInRole("ROLE_INTERNAL_USER")) {
                return responsibleEntitiesRepository.count()
                        .map(count -> {
                            tokenDetailsDTO.setCountResponsibleEntities(count);
                            return tokenDetailsDTO;
                        });
            } else {
                User principal = (User) authentication.getPrincipal();
                String userId = principal.getUsername().split("_")[0];
                return usersResponsibleEntityRepository.countByUserId(Integer.valueOf(userId))
                        .map(count -> {
                            tokenDetailsDTO.setCountResponsibleEntities(count);
                            return tokenDetailsDTO;
                        });
            }
        }).flatMap(tokenDetailsDTOMono -> tokenDetailsDTOMono);
    }

    private Mono<TokenDetailsDTO> createDTO(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TokenDetailsDTO tokenDetailsDTO = new TokenDetailsDTO();
        tokenDetailsDTO.setLogin(authentication.getName());
        List<String> roles = new ArrayList<>();
        authentication.getAuthorities().forEach(o -> roles.add(o.getAuthority()));
        tokenDetailsDTO.setRoles(roles);
        tokenDetailsDTO.setToken(token);
        return Mono.just(tokenDetailsDTO);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
        }
        return false;
    }

}
