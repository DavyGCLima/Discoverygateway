package com.example.eureka_gateway.eureka_gateway.repository;

import com.example.eureka_gateway.eureka_gateway.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    Mono<User> findOneByLegalNumber(String legalNumber);
}
