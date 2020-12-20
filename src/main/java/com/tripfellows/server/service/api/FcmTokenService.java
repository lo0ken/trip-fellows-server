package com.tripfellows.server.service.api;

public interface FcmTokenService {

    /**
     * Create an association of specified account with fcm token
     * Or update fcm token for existing association
     *
     * @param accountId id of account to associate with fcm token
     * @param fcmToken fcm token to associate with account
     */
    void update(Integer accountId, String fcmToken);

    /**
     * Delete an association of specified account with fcm token
     * Or do nothing if association is not exists
     *
     * @param accountId id of account to delete an association
     */
    void delete(Integer accountId);
}
