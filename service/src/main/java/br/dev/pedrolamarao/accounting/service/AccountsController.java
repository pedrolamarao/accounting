// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.micronaut.http.HttpStatus.NOT_FOUND;

@Controller("/accounts")
public class AccountsController
{
    private final AccountingService accounts;

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    public AccountsController (AccountingService accounts)
    {
        this.accounts = accounts;

        logger.info("<init>: accounts = {}", accounts);
    }

    @Post
    @Status(HttpStatus.CREATED)
    public AccountingAccount createAccount (AccountingAccount account)
    {
        final long accountId = accounts.createAccount(account);
        return new AccountingAccount(accountId,account.type(),account.name());
    }

    @Delete("/{accountId}")
    public void deleteAccount (@PathVariable long accountId)
    {
        accounts.deleteAccount(accountId);
    }

    @Get
    public List<AccountingAccount> listAccounts (@QueryValue(defaultValue="0") int page)
    {
        return accounts.listAccount(page);
    }

    @Get("/{accountId}")
    public AccountingAccount retrieveAccount (@PathVariable long accountId)
    {
        final var account = accounts.retrieveAccount(accountId);
        if (account == null) throw new HttpStatusException(NOT_FOUND,"");
        return account;
    }

    @Put("/{accountId}")
    public AccountingAccount updateAccount (@PathVariable long accountId, AccountingAccount account)
    {
        final var previous = accounts.updateAccount(accountId,account);
        if (previous == null) throw new HttpStatusException(NOT_FOUND,"");
        return account;
    }
}
