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

import java.net.URI;

import static io.micronaut.http.HttpStatus.NOT_FOUND;

@Controller("/accounts")
public class AccountingController
{
    private final AccountingService accounts;

    private final HttpHostResolver hostResolver;

    private static final Logger logger = LoggerFactory.getLogger(AccountingController.class);

    public AccountingController (AccountingService accounts, HttpHostResolver hostResolver)
    {
        this.accounts = accounts;
        this.hostResolver = hostResolver;

        logger.info("<init>: accounts = {}, host-resolver = {}", accounts, hostResolver);
    }

    @Post
    @Status(HttpStatus.CREATED)
    public Stored<AccountingAccount> createAccount (HttpRequest<?> request, AccountingAccount account)
    {
        final long accountId = accounts.createAccount(account);
        return new Stored<>(
            URI.create( hostResolver.resolve(request) + "/accounts/" + accountId ),
            account
        );
    }

    @Delete("/{accountId}")
    public void deleteAccount (@PathVariable long accountId)
    {
        accounts.deleteAccount(accountId);
    }

    @Get
    public Paged<Stored<AccountingAccount>> listAccounts (HttpRequest<?> request, @QueryValue(defaultValue="0") int page)
    {
        final var list = accounts.listAccount(page).stream()
                .map(it ->
                    new Stored<>(
                        URI.create( hostResolver.resolve(request) + "/accounts/" + it.id()),
                        it
                    )
                )
                .toList();
        return new Paged<>(
            URI.create( hostResolver.resolve(request) + "/accounts?page=" + page ),
            null,
            null,
            list
        );
    }

    @Get("/{accountId}")
    public Stored<AccountingAccount> retrieveAccount (HttpRequest<?> request, @PathVariable long accountId)
    {
        final var account = accounts.retrieveAccount(accountId);
        if (account == null) throw new HttpStatusException(NOT_FOUND,"");
        return new Stored<>( URI.create( hostResolver.resolve(request) + "/accounts/" + accountId ), account );
    }

    @Put("/{accountId}")
    public Stored<AccountingAccount> updateAccount (HttpRequest<?> request, @PathVariable long accountId, AccountingAccount account)
    {
        final var previous = accounts.updateAccount(accountId,account);
        if (previous == null) throw new HttpStatusException(NOT_FOUND,"");
        return new Stored<>(
            URI.create( hostResolver.resolve(request) + "/accounts/" + accountId ),
            account
        );
    }

    // transactions

    @Post("/{accountId}/transactions")
    @Status(HttpStatus.CREATED)
    public Stored<AccountingTransaction> createTransaction (HttpRequest<?> request, @PathVariable long accountId, AccountingTransaction transaction)
    {
        if (transaction.account() != accountId) throw new RuntimeException("oops");
        final long transactionId = accounts.createTransaction(transaction);
        return new Stored<>(
            transactionUri(request,accountId,transactionId),
            transaction
        );
    }

    @Delete("/{accountId}/transactions/{transactionId}")
    public void deleteTransaction (HttpRequest<?> request, @PathVariable long accountId, @PathVariable long transactionId)
    {
        // #TODO: validate accountId?
        accounts.deleteTransaction(transactionId);
    }

    @Get("/{accountId}/transactions")
    public Paged<Stored<AccountingTransaction>> listTransactions (HttpRequest<?> request, @PathVariable long accountId, @QueryValue(defaultValue="0") int page)
    {
        final var list = accounts.listTransactions(accountId,page).stream()
                .map(it ->
                    new Stored<>(
                        transactionUri(request,accountId,it.id()),
                        it
                    )
                )
                .toList();
        return new Paged<>(
            URI.create( hostResolver.resolve(request) + "/accounts/" + accountId + "/transactions?page=" + page ),
            null,
            null,
            list
        );
    }

    @Get("/{accountId}/transactions/{transactionId}")
    public Stored<AccountingTransaction> retrieveTransaction (HttpRequest<?> request, @PathVariable long accountId, @PathVariable long transactionId)
    {
        return new Stored<>(
            transactionUri(request,accountId,transactionId),
            accounts.retrieveTransaction(transactionId)
        );
    }

    @Put("/{accountId}/transactions/{transactionId}")
    public Stored<AccountingTransaction> updateTransaction (HttpRequest<?> request, @PathVariable long accountId, @PathVariable long transactionId, AccountingTransaction transaction)
    {
        // #TODO: validate accountId?
        accounts.updateTransaction(transaction);
        return new Stored<>(
            transactionUri(request,accountId,transactionId),
            transaction
        );
    }

    public URI transactionUri (HttpRequest<?> base, long accountId, long transactionId)
    {
        return URI.create( hostResolver.resolve(base) + "/accounts/" + accountId + "/transactions/" + transactionId );
    }
}
