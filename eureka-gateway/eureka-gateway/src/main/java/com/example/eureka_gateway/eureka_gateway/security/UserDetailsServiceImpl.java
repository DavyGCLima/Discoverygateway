package com.example.eureka_gateway.eureka_gateway.security;

import com.example.eureka_gateway.eureka_gateway.repository.UserRepository;
import com.example.eureka_gateway.eureka_gateway.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        return userRepository.findOneByLegalNumber(login)
            .filter(user -> SecurityUtils.USER_ACTIVE.equals(user.getStatus()))
            .map(UserAuthenticated::new)
            .orElseThrow(() -> new UsernameNotFoundException("User with legal number " + login + " was not found in the database"));
    }
}

