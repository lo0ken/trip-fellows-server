package com.tripfellows.server.security;

import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecurityService {

    public String getAuthenticatedUserUid() {
        FirebaseToken token = (FirebaseToken) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return token.getUid();
    }
}
