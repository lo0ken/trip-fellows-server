package com.tripfellows.server.service.impl;

import com.tripfellows.server.entity.AccountEntity;
import com.tripfellows.server.mapper.AccountMapper;
import com.tripfellows.server.model.Account;
import com.tripfellows.server.repository.AccountRepository;
import com.tripfellows.server.service.api.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;


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

    @Override
    public Optional<Account> findById(Integer id) {
        log.debug("Retrieving account with id {}", id);
        Optional<AccountEntity> accountEntityOptional = accountRepository.findById(id);

        return accountEntityOptional.map(accountMapper::map);
    }
    @Override
    public void deleteById(Integer id) {
        log.debug("Delete account with identifier {}", id);
        accountRepository.deleteById(id);


    }
    @Override
    public Account save(Account account) {
        AccountEntity saved = accountRepository.save(accountMapper.map(account));
        log.debug("Account with id {} has been saved", saved.getId());

        return accountMapper.map(saved);
    }
}
