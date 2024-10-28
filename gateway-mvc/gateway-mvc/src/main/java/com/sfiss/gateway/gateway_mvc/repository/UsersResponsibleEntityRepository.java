package com.sfiss.gateway.gateway_mvc.repository;


import com.sfiss.gateway.gateway_mvc.domain.UserResponsibleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersResponsibleEntityRepository extends JpaRepository<UserResponsibleEntity, Long> {

    Long countByUserId(Integer userId);

}
