package com.example.eureka_gateway.eureka_gateway.repository;

import com.example.eureka_gateway.eureka_gateway.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByLegalNumber(String legalNumber);
}
