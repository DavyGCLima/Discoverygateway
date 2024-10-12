package com.example.eureka_gateway.eureka_gateway.security;

import com.example.eureka_gateway.eureka_gateway.repository.UserRepository;
import com.example.eureka_gateway.eureka_gateway.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(userRepository.findOneByLegalNumber(username)
                .filter(user -> SecurityUtils.USER_ACTIVE.equals(user.getStatus()))
                .map(UserAuthenticated::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with legal number " + username + " was not found in the database")));
    }
}

