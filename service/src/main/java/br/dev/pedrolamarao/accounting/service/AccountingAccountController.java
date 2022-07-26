// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class AccountingAccountController
{
    private final AtomicInteger counter = new AtomicInteger();

    @Get("/accounts")
    public List<AccountingAccount> listAccounts ()
    {
        return Collections.emptyList();
    }

    @Get("/accounts/{accountId}")
    public AccountingAccount getAccount (@PathVariable String accountId)
    {
        return new AccountingAccount("description");
    }

    @Put("/accounts/{accountId}")
    public AccountingAccount updateAccount (@PathVariable String accountId, AccountingAccount account)
    {
        return account;
    }

    @Get("/accounts/{accountId}/transactions")
    public List<AccountingTransaction> listTransactions (@PathVariable String accountId)
    {
        return Collections.emptyList();
    }
}
