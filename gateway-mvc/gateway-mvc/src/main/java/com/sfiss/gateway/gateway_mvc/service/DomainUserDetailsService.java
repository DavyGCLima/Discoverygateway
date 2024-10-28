package com.sfiss.gateway.gateway_mvc.service;

import com.sfiss.gateway.gateway_mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        return userRepository.findOneByLegalNumber(login)
            .filter(user -> user.getStatus().equals(com.sfiss.gateway.gateway_mvc.domain.User.USER_ACTIVE))
            .map(this::createSpringSecurityUser)
            .orElseThrow(() -> new UsernameNotFoundException("User with legal number " + login + " was not found in the database"));
    }

    private User createSpringSecurityUser(com.sfiss.gateway.gateway_mvc.domain.User user) {
        return new User(user.getId()+"_"+user.getLegalNumber(),
            user.getPassword(),
            user.getAuthorities());
    }
}

