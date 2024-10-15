package com.example.eureka_gateway.eureka_gateway.repository;

import com.example.eureka_gateway.eureka_gateway.domain.UserResponsibleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersResponsibleEntityRepository extends JpaRepository<UserResponsibleEntity, Long> {
    Long countByUserId(Integer userId);
}
