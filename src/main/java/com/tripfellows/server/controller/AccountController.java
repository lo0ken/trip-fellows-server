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
     * GET /api/accounts/:id : get the account with specified id
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

    /**
     * POST /api/accounts : Create a new account
     *
     * @param account the account to create
     * @return the ResponseEntity with status 201 (Created) and with body the new account, or with status 400 (Bad Request) if the account has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
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

    /**
     * PUT /api/accounts : Updates an existing account
     *
     * @param account the account to update
     * @return the ResponseEntity with status 200 (OK) and with body the account,
     * or with status 400 (Bad Request) if the account is not valid,
     * or with status 500 (Internal Server Error) if the account couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) throws URISyntaxException {
        log.debug("REST request to update Account : {}", account);

        if (isNull(account.getId())) {
            return createAccount(account);
        }

        Account result = accountService.save(account);

        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{id}")
    public void   deleteAccount(@PathVariable Integer id) {
        log.debug("REST request to get Account with id {}", id);

        accountService.deleteById(id);
    }
}
