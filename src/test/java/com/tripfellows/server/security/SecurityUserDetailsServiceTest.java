package com.tripfellows.server.security;

import com.tripfellows.server.entity.UserEntity;
import com.tripfellows.server.enums.UserRole;
import com.tripfellows.server.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    SecurityUserDetailsService userDetailsService;

    @Test
    public void usernameNotFoundTest() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(""));
    }

    @Test
    public void loadByUsernameTest() {
        UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);

        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getUsername());

        assertEquals(userDetails.getUsername(), userEntity.getUsername());
        assertEquals(userDetails.getPassword(), userEntity.getPassword());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.getAuthorities().contains(UserRole.USER));
    }
}