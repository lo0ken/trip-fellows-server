package com.tripfellows.server.security;

import com.tripfellows.server.model.Account;

public interface SecurityService {
    /**
     * Get authenticated account uid from SecurityContextHolder
     *
     * @return authenticated account uid
     */
    String getAuthenticatedUserUid();

    /**
     * Retrieving account from database with current authenticated user uid
     *
     * @return account of authenticated user
     */
    Account getCurrentAccount();
}
