package com.tripfellows.server.controller;

import com.tripfellows.server.model.Account;
import com.tripfellows.server.model.request.UpdateFcmTokenRequest;
import com.tripfellows.server.security.SecurityService;
import com.tripfellows.server.service.api.FcmTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing fcm tokens
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/fcm")
public class FcmController {

    private final SecurityService securityService;

    private final FcmTokenService fcmTokenService;

    /**
     * POST: /api/fcm/updateFcmToken : creates an association of account with fcm token or updates it if exists
     *
     * @param updateFcmTokenRequest fcm token to associate
     * @return the ResponseEntity with status OK (200) if an operation completed successfully
     */
    @PostMapping("/updateFcmToken")
    public ResponseEntity<Void> updateFcmToken(@RequestBody UpdateFcmTokenRequest updateFcmTokenRequest) {
        Account account = securityService.getCurrentAccount();
        log.debug("REST request to update fcm token for account with id: {}", account.getId());

        fcmTokenService.update(account.getId(), updateFcmTokenRequest.getFcmToken());
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE: /api/fcm/deleteFcmToken : deletes an association of account with fcm token
     *
     * @return the ResponseEntity with status OK (200) if an operation completed successfully
     */
    @DeleteMapping("/deleteFcmToken")
    public ResponseEntity<Void> deleteFcmToken() {
        Account account = securityService.getCurrentAccount();
        log.debug("REST request to delete fcm token for account with id: {}", account.getId());

        fcmTokenService.delete(account.getId());
        return ResponseEntity.ok().build();
    }
}