package com.tripfellows.server.service.impl;

import com.tripfellows.server.entity.AccountFcmToken;
import com.tripfellows.server.repository.AccountFcmTokenRepository;
import com.tripfellows.server.service.api.FcmTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for fcm tokens
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FcmTokenServiceImpl implements FcmTokenService {

    private final AccountFcmTokenRepository fcmTokenRepository;

    @Override
    public void update(Integer accountId, String fcmToken) {
        log.debug("Updating fcm token: {} for account with id: {}", accountId, fcmToken);
        Optional<AccountFcmToken> existingToken = fcmTokenRepository.findById(accountId);

        AccountFcmToken accountFcmToken;
        if (existingToken.isPresent()) {
            accountFcmToken = existingToken.get();
            accountFcmToken.setFcmToken(fcmToken);
        } else {
            accountFcmToken = new AccountFcmToken(accountId, fcmToken);
        }

        fcmTokenRepository.save(accountFcmToken);
    }

    @Override
    public void delete(Integer accountId) {
        if (fcmTokenRepository.existsById(accountId)) {
            log.debug("Deleting fcm token of account with id: {}", accountId);
            fcmTokenRepository.deleteById(accountId);
        }
    }
}
