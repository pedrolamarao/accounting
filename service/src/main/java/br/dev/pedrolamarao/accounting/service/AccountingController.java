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

@Controller
public class AccountingController
{
    private final AccountingService accounts;

    private static final Logger logger = LoggerFactory.getLogger(AccountingController.class);

    public AccountingController (AccountingService accounts)
    {
        this.accounts = accounts;

        logger.info("<init>: accounts = {}", accounts);
    }

    @Post("/accounts")
    @Status(HttpStatus.CREATED)
    public AccountingAccount createAccount (AccountingAccount account)
    {
        final long accountId = accounts.createAccount(account);
        return new AccountingAccount(accountId,account.type(),account.name());
    }

    @Delete("/accounts/{accountId}")
    public void deleteAccount (@PathVariable long accountId)
    {
        accounts.deleteAccount(accountId);
    }

    @Get("/accounts")
    public List<AccountingAccount> listAccounts (@QueryValue(defaultValue="0") int page)
    {
        return accounts.listAccount(page);
    }

    @Get("/accounts/{accountId}")
    public AccountingAccount retrieveAccount (@PathVariable long accountId)
    {
        final var account = accounts.retrieveAccount(accountId);
        if (account == null) throw new HttpStatusException(NOT_FOUND,"");
        return account;
    }

    @Put("/accounts/{accountId}")
    public AccountingAccount updateAccount (@PathVariable long accountId, AccountingAccount account)
    {
        final var previous = accounts.updateAccount(accountId,account);
        if (previous == null) throw new HttpStatusException(NOT_FOUND,"");
        return account;
    }

    // transactions

    @Post("/transactions")
    @Status(HttpStatus.CREATED)
    public AccountingTransaction createTransaction (AccountingTransaction transaction)
    {
        final long transactionId = accounts.createTransaction(transaction);
        return new AccountingTransaction(transactionId,transaction.account(),transaction.type(),transaction.date(),transaction.moneys(),transaction.description());
    }

    @Delete("/transactions/{transactionId}")
    public void deleteTransaction (@PathVariable long transactionId)
    {
        accounts.deleteTransaction(transactionId);
    }

    @Get("/transactions")
    public List<AccountingTransaction> listTransactions (@QueryValue long account, @QueryValue(defaultValue="0") int page)
    {
        return accounts.listTransactions(account,page);
    }

    @Get("/transactions/{transactionId}")
    public AccountingTransaction retrieveTransaction (@PathVariable long transactionId)
    {
        return accounts.retrieveTransaction(transactionId);
    }

    @Put("/transactions/{transactionId}")
    public AccountingTransaction updateTransaction (@PathVariable long transactionId, AccountingTransaction transaction)
    {
        accounts.updateTransaction(transaction);
        return transaction;
    }
}
