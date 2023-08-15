// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller("/accounts")
class AccountsController
{
    private final AccountRepository repository;

    private final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    AccountsController (AccountRepository repository)
    {
        this.repository = repository;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public List<AccountingAccount> createAccount (List<AccountingAccount> accounts)
    {
         return accounts.stream().map(repository::save).toList();
    }

    @Delete("/{accountId}")
    public void deleteAccount (@PathVariable long accountId)
    {
        repository.deleteById(accountId);
    }

    @Get
    public Iterable<AccountingAccount> listAccounts ()
    {
        return repository.findAll();
    }

    @Get("/{accountId}")
    public AccountingAccount retrieveAccount (@PathVariable long accountId)
    {
        return repository.findById(accountId).orElseThrow();
    }

    @Put("/{accountId}")
    public AccountingAccount updateAccount (@PathVariable long accountId, AccountingAccount account)
    {
        assert account.id() == accountId;
        return repository.update(account);
    }
}
