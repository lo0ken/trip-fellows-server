package com.tripfellows.server.repository;

import com.tripfellows.server.entity.TripAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripAccountRepository extends JpaRepository<TripAccountEntity, Integer> {
}
