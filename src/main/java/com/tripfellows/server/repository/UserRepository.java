package com.tripfellows.server.repository;

import com.tripfellows.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
