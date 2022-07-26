// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.*;
import io.micronaut.http.server.util.HttpHostResolver;

import java.net.URI;

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
    public Stored<AccountingAccount> createAccount (HttpRequest<?> request, AccountingAccount account)
    {
        final long accountId = accounts.create(account);
        return new Stored<>(
            URI.create( hostResolver.resolve(request) + "/accounts/" + accountId ),
            account
        );
    }

    @Get("/{accountId}")
    public void deleteAccount (HttpRequest<?> request, @PathVariable long accountId)
    {
        accounts.delete(accountId);
    }

    @Get
    public Paged<Stored<AccountingAccount>> listAccounts (HttpRequest<?> request, @QueryValue(defaultValue="0") int page)
    {
        final var list = accounts.list(page).stream()
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
        return new Stored<>(
            URI.create( hostResolver.resolve(request) + "/accounts/" + accountId ),
            accounts.retrieve(accountId)
        );
    }

    @Put("/{accountId}")
    public Stored<AccountingAccount> updateAccount (HttpRequest<?> request, @PathVariable long accountId, AccountingAccount account)
    {
        accounts.update(accountId,account);
        return new Stored<>(
            URI.create( hostResolver.resolve(request) + "/accounts/" + accountId ),
            account
        );
    }
}
