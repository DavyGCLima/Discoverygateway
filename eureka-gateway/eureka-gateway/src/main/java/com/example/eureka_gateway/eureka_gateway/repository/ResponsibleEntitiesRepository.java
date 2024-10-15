package com.example.eureka_gateway.eureka_gateway.repository;

import com.example.eureka_gateway.eureka_gateway.domain.ResponsibleEntities;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ResponsibleEntitiesRepository extends R2dbcRepository<ResponsibleEntities, Integer> {
}
