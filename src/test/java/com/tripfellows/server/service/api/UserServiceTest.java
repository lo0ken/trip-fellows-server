package com.tripfellows.server.service.api;

import com.tripfellows.server.exception.UsernameAlreadyExistsException;
import com.tripfellows.server.mapper.AccountMapper;
import com.tripfellows.server.model.Account;
import com.tripfellows.server.model.request.SignUpRequest;
import com.tripfellows.server.repository.UserRepository;
import com.tripfellows.server.service.impl.UserServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AccountService accountService;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    EasyRandom easyRandom = new EasyRandom();

    AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    @Test
    public void signUpTest() {
        SignUpRequest request = easyRandom.nextObject(SignUpRequest.class);
        Integer idForAccount = 10;

        Account createdAccount = accountMapper.map(request);
        createdAccount.setId(idForAccount);

        when(accountService.save(any())).thenReturn(createdAccount);

        userService.signUp(request);

        verify(accountService).save(argThat(ac -> ac.getName().equals(createdAccount.getName())));
        verify(userRepository).save(argThat(u -> u.getAccount().getId().equals(createdAccount.getId())));
    }

    @Test
    public void usernameAlreadyExistsTest() {
        SignUpRequest request = easyRandom.nextObject(SignUpRequest.class);

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.signUp(request));
    }
}