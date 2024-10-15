package com.example.eureka_gateway.eureka_gateway.repository;

import com.example.eureka_gateway.eureka_gateway.domain.UserResponsibleEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UsersResponsibleEntityRepository extends R2dbcRepository<UserResponsibleEntity, Long> {
    Mono<Long> countByUserId(Integer userId);
}
