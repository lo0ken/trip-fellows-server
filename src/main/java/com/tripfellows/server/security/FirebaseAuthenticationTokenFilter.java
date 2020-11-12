package com.tripfellows.server.security;

import com.google.api.client.util.Strings;
import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.tripfellows.server.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class FirebaseAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("FirebaseAuthenticationTokenFilter: authenticating");

        String authToken = request.getHeader(TOKEN_HEADER);

        if (Strings.isNullOrEmpty(authToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = getAndValidateAuthentication(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Successfully authenticated.");
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            logger.debug("Authentication failed", ex);
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAndValidateAuthentication(String authToken) throws Exception {
        FirebaseToken firebaseToken = authenticateFirebaseToken(authToken);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(firebaseToken, authToken, Collections.singleton(UserRole.USER));

        return authentication;
    }

    private FirebaseToken authenticateFirebaseToken(String authToken) throws Exception {
        authToken = authToken.substring(7);
        ApiFuture<FirebaseToken> app = FirebaseAuth.getInstance().verifyIdTokenAsync(authToken);
        return app.get();
    }
}
