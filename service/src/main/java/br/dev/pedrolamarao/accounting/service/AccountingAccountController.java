// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.*;
import io.micronaut.http.server.util.HttpHostResolver;

import java.net.URI;

@Controller
public class AccountingAccountController
{
    private final AccountingAccountService accounts;

    private final HttpHostResolver hostResolver;

    public AccountingAccountController (AccountingAccountService accounts, HttpHostResolver hostResolver)
    {
        this.accounts = accounts;
        this.hostResolver = hostResolver;
    }

    @Post("/accounts")
    public Stored<AccountingAccount> createAccount (HttpRequest<?> request, AccountingAccount account)
    {
        final long id = accounts.create(account);
        return new Stored<>(
            URI.create( hostResolver.resolve(request) + "/accounts/" + id ),
            account
        );
    }

    @Get("/accounts/{accountId}")
    public void deleteAccount (HttpRequest<?> request, @PathVariable String accountId)
    {
        final long id = Long.parseLong(accountId);
        accounts.delete(id);
    }

    @Get("/accounts")
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

    @Get("/accounts/{accountId}")
    public Stored<AccountingAccount> retrieveAccount(HttpRequest<?> request, @PathVariable String accountId)
    {
        final long id = Long.parseLong(accountId);
        return new Stored<>(
            URI.create( hostResolver.resolve(request) + "/accounts/" + id ),
            accounts.retrieve(id)
        );
    }

    @Put("/accounts/{accountId}")
    public Stored<AccountingAccount> updateAccount (HttpRequest<?> request, @PathVariable String accountId, AccountingAccount account)
    {
        final long id = Long.parseLong(accountId);
        accounts.update(id,account);
        return new Stored<>(
            URI.create( hostResolver.resolve(request) + "/accounts/" + id ),
            account
        );
    }
}
