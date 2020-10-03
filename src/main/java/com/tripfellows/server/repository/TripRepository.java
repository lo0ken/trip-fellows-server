package com.tripfellows.server.repository;

import com.tripfellows.server.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Integer> {

    @Query("select tae.trip from TripAccountEntity tae where tae.account.id = :accountId")
    List<TripEntity> findByAccountId (@Param("accountId") Integer accountId);
}
