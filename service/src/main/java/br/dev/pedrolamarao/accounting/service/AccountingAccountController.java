// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.http.server.util.HttpHostResolver;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class AccountingAccountController
{
    private final HashMap<Long,Stored<AccountingAccount>> accounts = new HashMap<>();

    private final AtomicInteger counter = new AtomicInteger();

    private final HttpHostResolver httpHostResolver;

    public AccountingAccountController (HttpHostResolver httpHostResolver)
    {
        this.httpHostResolver = httpHostResolver;
    }


    @Get("/accounts")
    public Paged<Stored<AccountingAccount>> listAccounts (HttpRequest<?> request, @QueryValue(defaultValue="0") int page)
    {
        final var base = httpHostResolver.resolve(request) + "/accounts";
        final var current = URI.create("%s?page=%s".formatted(base,page));
        final var values = new ArrayList<>(accounts.values());
        return new Paged<>(current,null,null,values);
    }

    @Post("/accounts")
    public Stored<AccountingAccount> createAccount (HttpRequest<?> request, AccountingAccount account)
    {
        final long id = counter.getAndIncrement();
        final var uri = httpHostResolver.resolve(request) + "/accounts/" + id;
        final var stored = new Stored<>(URI.create(uri),account);
        accounts.put(id,stored);
        return stored;
    }

    @Get("/accounts/{accountId}")
    public Stored<AccountingAccount> getAccount (HttpRequest<?> request, @PathVariable String accountId)
    {
        final long id = Long.parseLong(accountId);
        final var account = accounts.get(id);
        if (account == null) throw new HttpStatusException(HttpStatus.NOT_FOUND,"");
        else return account;
    }

    @Put("/accounts/{accountId}")
    public Stored<AccountingAccount> updateAccount (HttpRequest<?> request, @PathVariable String accountId, AccountingAccount account)
    {
        final long id = Long.parseLong(accountId);
        final var uri = httpHostResolver.resolve(request) + "/accounts/" + id;
        final var stored = new Stored<>(URI.create(uri),account);
        accounts.put(id,stored);
        return stored;
    }
}
