package com.tripfellows.server.repository;

import com.tripfellows.server.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Integer> {

    @Query("select ta.trip from TripAccountEntity ta where ta.account.id = :accountId")
    List<TripEntity> findByAccountId(@Param("accountId") Integer accountId);

    @Query("select t from TripEntity t where t.status.id = (select ts.id from TripStatusEntity ts where ts.code = 'WAITING')")
    List<TripEntity> findAllActive();
}
