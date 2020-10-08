package com.tripfellows.server.repository;

import com.tripfellows.server.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Integer> {
}
