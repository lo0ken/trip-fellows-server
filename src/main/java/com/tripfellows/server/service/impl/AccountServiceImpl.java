package com.tripfellows.server.service.impl;

import com.tripfellows.server.mapper.AccountMapper;
import com.tripfellows.server.repository.AccountRepository;
import com.tripfellows.server.service.api.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service for Accounts
 */
@Slf4j
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        accountMapper = Mappers.getMapper(AccountMapper.class);
    }
}
