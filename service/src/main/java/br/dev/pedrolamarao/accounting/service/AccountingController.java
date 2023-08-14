// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.http.server.util.HttpHostResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.micronaut.http.HttpStatus.NOT_FOUND;

@Controller("/accounts")
public class AccountingController
{
    private final AccountingService accounts;

    private static final Logger logger = LoggerFactory.getLogger(AccountingController.class);

    public AccountingController (AccountingService accounts, HttpHostResolver hostResolver)
    {
        this.accounts = accounts;

        logger.info("<init>: accounts = {}, host-resolver = {}", accounts, hostResolver);
    }

    @Post
    @Status(HttpStatus.CREATED)
    public AccountingAccount createAccount (HttpRequest<?> request, AccountingAccount account)
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
    public List<AccountingAccount> listAccounts (HttpRequest<?> request, @QueryValue(defaultValue="0") int page)
    {
        return accounts.listAccount(page);
    }

    @Get("/{accountId}")
    public AccountingAccount retrieveAccount (HttpRequest<?> request, @PathVariable long accountId)
    {
        final var account = accounts.retrieveAccount(accountId);
        if (account == null) throw new HttpStatusException(NOT_FOUND,"");
        return account;
    }

    @Put("/{accountId}")
    public AccountingAccount updateAccount (HttpRequest<?> request, @PathVariable long accountId, AccountingAccount account)
    {
        final var previous = accounts.updateAccount(accountId,account);
        if (previous == null) throw new HttpStatusException(NOT_FOUND,"");
        return account;
    }

    // transactions

    @Post("/{accountId}/transactions")
    @Status(HttpStatus.CREATED)
    public AccountingTransaction createTransaction (HttpRequest<?> request, @PathVariable long accountId, AccountingTransaction transaction)
    {
        if (transaction.account() != accountId) throw new RuntimeException("oops");
        final long transactionId = accounts.createTransaction(transaction);
        return new AccountingTransaction(transactionId,accountId,transaction.type(),transaction.date(),transaction.moneys(),transaction.description());
    }

    @Delete("/{accountId}/transactions/{transactionId}")
    public void deleteTransaction (HttpRequest<?> request, @PathVariable long accountId, @PathVariable long transactionId)
    {
        // #TODO: validate accountId?
        accounts.deleteTransaction(transactionId);
    }

    @Get("/{accountId}/transactions")
    public List<AccountingTransaction> listTransactions (HttpRequest<?> request, @PathVariable long accountId, @QueryValue(defaultValue="0") int page)
    {
        return accounts.listTransactions(accountId,page);
    }

    @Get("/{accountId}/transactions/{transactionId}")
    public AccountingTransaction retrieveTransaction (HttpRequest<?> request, @PathVariable long accountId, @PathVariable long transactionId)
    {
        return accounts.retrieveTransaction(transactionId);
    }

    @Put("/{accountId}/transactions/{transactionId}")
    public AccountingTransaction updateTransaction (HttpRequest<?> request, @PathVariable long accountId, @PathVariable long transactionId, AccountingTransaction transaction)
    {
        accounts.updateTransaction(transaction);
        return transaction;
    }
}
