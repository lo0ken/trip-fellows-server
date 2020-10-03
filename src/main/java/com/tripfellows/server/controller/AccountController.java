package com.tripfellows.server.controller;

import com.tripfellows.server.model.Account;
import com.tripfellows.server.service.api.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * REST controller for managing accounts
 */
@Slf4j
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * GET /accounts/:id : get the account with specified id
     *
     * @param id the id of the account to retrieve
     * @return the ResponseEntity with the status 200 (OK) and with body the account, or with status 400 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id) {
        log.debug("REST request to get Account with id {}", id);

        Optional<Account> account = accountService.findById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) throws URISyntaxException {
        log.debug("REST request to create account");

        if (!isNull(account.getId())) {
            return ResponseEntity.badRequest().body(null);
        }

        Account result = accountService.save(account);
        return ResponseEntity.created(new URI("/api/accounts/" + result.getId()))
                .body(result);
    }
}
