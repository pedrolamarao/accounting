// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.http.server.util.HttpHostResolver;

import java.net.URI;

import static io.micronaut.http.HttpStatus.NOT_FOUND;

@Controller("/accounts")
public class AccountingAccountController
{
    private final AccountingAccountService accounts;

    private final HttpHostResolver hostResolver;

    public AccountingAccountController (AccountingAccountService accounts, HttpHostResolver hostResolver)
    {
        this.accounts = accounts;
        this.hostResolver = hostResolver;
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
                        it.value()
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
    public Stored<AccountingTransaction> createTransaction (HttpRequest<?> request, @PathVariable long accountId, AccountingTransaction transaction)
    {
        final long transactionId = accounts.createTransaction(accountId,transaction);
        return new Stored<>(
            transactionUri(request,accountId,transactionId),
            transaction
        );
    }

    @Delete("/{accountId}/transactions/{transactionId}")
    public void deleteTransaction (HttpRequest<?> request, @PathVariable long accountId, @PathVariable long transactionId)
    {
        accounts.deleteTransaction(accountId,transactionId);
    }

    @Get("/{accountId}/transactions")
    public Paged<Stored<AccountingTransaction>> listTransactions (HttpRequest<?> request, @PathVariable long accountId, @QueryValue(defaultValue="0") int page)
    {
        final var list = accounts.listTransactions(accountId,page).stream()
                .map(it ->
                    new Stored<>(
                        transactionUri(request,accountId,it.id()),
                        it.value()
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
            accounts.retrieveTransaction(accountId,transactionId)
        );
    }

    @Put("/{accountId}/transactions/{transactionId}")
    public Stored<AccountingTransaction> updateTransaction (HttpRequest<?> request, @PathVariable long accountId, @PathVariable long transactionId, AccountingTransaction transaction)
    {
        accounts.updateTransaction(accountId,transactionId,transaction);
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
