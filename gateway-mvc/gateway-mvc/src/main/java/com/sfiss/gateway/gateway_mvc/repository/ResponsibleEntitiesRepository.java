package com.sfiss.gateway.gateway_mvc.repository;

import com.sfiss.gateway.gateway_mvc.domain.ResponsibleEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsibleEntitiesRepository extends JpaRepository<ResponsibleEntities, Integer> {

}
