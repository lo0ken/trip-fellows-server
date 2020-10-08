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

    /**
     * Update all information for a specific account, and return the modified account
     * Or create it if not exists
     *
     * @param account account to create or update
     * @return updated or created account
     */
    Account save(Account account);

    /**
     * Delete account from data base
     * @param id of account to delete
     */
    void deleteById(Integer id);
}
