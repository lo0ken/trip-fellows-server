package com.tripfellows.server.service.api;

import com.tripfellows.server.model.Account;

import java.util.Optional;

public interface AccountService {

    /**
     * Retrieve account from database
     * @param id account id to retrieve
     * @return Optional with body the account or Optional.empty
     */
    Optional<Account> findById(Integer id);
}
