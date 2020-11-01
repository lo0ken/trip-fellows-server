package com.tripfellows.server.service.impl;

import com.tripfellows.server.entity.UserEntity;
import com.tripfellows.server.enums.UserRole;
import com.tripfellows.server.exception.UsernameAlreadyExistsException;
import com.tripfellows.server.mapper.AccountMapper;
import com.tripfellows.server.model.Account;
import com.tripfellows.server.model.request.SignUpRequest;
import com.tripfellows.server.repository.UserRepository;
import com.tripfellows.server.service.api.AccountService;
import com.tripfellows.server.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for users
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AccountService accountService;

    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    @Override
    public void signUp(SignUpRequest request) {
        log.debug("Trying to sign up user with username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        UserEntity userEntity = makeEntity(request);
        Account account = accountMapper.map(request);

        Account userAccount = accountService.save(account);
        userEntity.setAccount(accountMapper.map(userAccount));

        userRepository.save(userEntity);
    }

    private UserEntity makeEntity(SignUpRequest request) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(UserRole.USER));
        return user;
    }
}
