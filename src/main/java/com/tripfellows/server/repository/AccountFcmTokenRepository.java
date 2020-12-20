package com.tripfellows.server.repository;

import com.tripfellows.server.entity.AccountFcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountFcmTokenRepository extends JpaRepository<AccountFcmToken, Integer> {

}
