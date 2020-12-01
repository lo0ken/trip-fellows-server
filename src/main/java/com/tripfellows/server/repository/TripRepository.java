package com.tripfellows.server.repository;

import com.tripfellows.server.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Integer> {

    @Query("select ta.trip from TripAccountEntity ta where ta.account.id = :accountId")
    List<TripEntity> findByAccountId(@Param("accountId") Integer accountId);

    @Query("select t from TripEntity t where t.status.id = (select ts.id from TripStatusEntity ts where ts.code = 'WAITING')")
    List<TripEntity> findAllActive();

    @Query("select t from TripEntity t where t.creator.id = :accountId and" +
            " (t.status.id = (select ts.id from TripStatusEntity ts where ts.code = 'STARTED')" +
            " or t.status.id = (select ts.id from TripStatusEntity ts where ts.code = 'WAITING'))")
    Optional<TripEntity> findCurrentDriverTrip(@Param("accountId") Integer accountId);


    @Query("select ts.trip from TripAccountEntity ts where ts.account.id = :accountId and " +
            "(ts.trip.status.id = (select ts.id from TripStatusEntity ts where ts.code = 'STARTED') " +
            "or ts.trip.status.id = (select ts.id from TripStatusEntity ts where ts.code = 'WAITING'))")
    Optional<TripEntity> findCurrentPassengerTrip(@Param("accountId") Integer accountId);
}
