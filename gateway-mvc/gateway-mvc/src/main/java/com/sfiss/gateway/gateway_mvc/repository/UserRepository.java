package com.sfiss.gateway.gateway_mvc.repository;

import com.sfiss.gateway.gateway_mvc.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByLegalNumber(String legalNumber);
}

