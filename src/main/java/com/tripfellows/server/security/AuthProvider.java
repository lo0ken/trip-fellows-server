package com.tripfellows.server.security;

import com.tripfellows.server.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final SecurityUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        log.debug("Authentication user with username: {}", username);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!isNull(username) && (userDetails.getUsername().equals(username))) {
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new InvalidCredentialsException();
            }

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        }
        throw new InvalidCredentialsException();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
