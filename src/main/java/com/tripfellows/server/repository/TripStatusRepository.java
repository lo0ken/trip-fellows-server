package com.tripfellows.server.repository;

import com.tripfellows.server.entity.TripStatusEntity;
import com.tripfellows.server.enums.TripStatusCodeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripStatusRepository extends JpaRepository<TripStatusEntity, Integer> {
    TripStatusEntity findByCode(TripStatusCodeEnum code);
}
