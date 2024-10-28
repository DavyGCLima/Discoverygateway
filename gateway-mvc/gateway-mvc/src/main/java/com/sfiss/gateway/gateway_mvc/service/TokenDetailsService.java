package com.sfiss.gateway.gateway_mvc.service;

import com.sfiss.gateway.gateway_mvc.DTO.TokenDetailsDTO;
import com.sfiss.gateway.gateway_mvc.repository.ResponsibleEntitiesRepository;
import com.sfiss.gateway.gateway_mvc.repository.UsersResponsibleEntityRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenDetailsService {

    private final ResponsibleEntitiesRepository responsibleEntitiesRepository;

    private final UsersResponsibleEntityRepository usersResponsibleEntityRepository;

    public TokenDetailsDTO getTokenDetails(String token) {
        Authentication authentication = getAuthentication();
        TokenDetailsDTO tokenDetailsDTO = new TokenDetailsDTO();
        tokenDetailsDTO.setLogin(authentication.getName());
        List<String> roles = new ArrayList<>();
        authentication.getAuthorities().forEach(o -> roles.add(o.getAuthority()));
        tokenDetailsDTO.setRoles(roles);
        tokenDetailsDTO.setToken(token);

        if(isCurrentUserInRole("ROLE_INTERNAL_USER")) {
            tokenDetailsDTO.setCountResponsibleEntities(responsibleEntitiesRepository.count());
        } else {
            User principal = (User) authentication.getPrincipal();
            String userId = principal.getUsername().split("_")[0];
            tokenDetailsDTO.setCountResponsibleEntities(usersResponsibleEntityRepository.countByUserId(Integer.valueOf(userId)));
        }

        return tokenDetailsDTO;
    }

    public static Authentication getAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }

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
