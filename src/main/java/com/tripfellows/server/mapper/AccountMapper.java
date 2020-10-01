package com.tripfellows.server.mapper;

import com.tripfellows.server.entity.AccountEntity;
import com.tripfellows.server.model.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    Account map(AccountEntity entity);
    AccountEntity map(Account account);
}
