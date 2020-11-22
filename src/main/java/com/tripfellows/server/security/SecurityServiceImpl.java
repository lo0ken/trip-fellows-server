package com.tripfellows.server.security;

import com.google.firebase.auth.FirebaseToken;
import com.tripfellows.server.exception.AuthenticationFailedException;
import com.tripfellows.server.model.Account;
import com.tripfellows.server.service.api.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final AccountService accountService;

    @Override
    public String getAuthenticatedUserUid() {
        FirebaseToken token = (FirebaseToken) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return token.getUid();
    }

    @Override
    public Account getCurrentAccount() {
        String uid = getAuthenticatedUserUid();

        Optional<Account> accountOptional = accountService.findByUid(uid);
        return accountOptional.orElseThrow(AuthenticationFailedException::new);
    }
}
