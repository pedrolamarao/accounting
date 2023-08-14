// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.micronaut.http.HttpStatus.NOT_FOUND;

@Controller("/transactions")
public class TransactionsController
{
    private final AccountingService accounts;

    private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    public TransactionsController (AccountingService accounts)
    {
        this.accounts = accounts;

        logger.info("<init>: accounts = {}", accounts);
    }

    @Post
    @Status(HttpStatus.CREATED)
    public AccountingTransaction createTransaction (AccountingTransaction transaction)
    {
        final long transactionId = accounts.createTransaction(transaction);
        return new AccountingTransaction(transactionId,transaction.account(),transaction.type(),transaction.date(),transaction.moneys(),transaction.description());
    }

    @Delete("/{transactionId}")
    public void deleteTransaction (@PathVariable long transactionId)
    {
        accounts.deleteTransaction(transactionId);
    }

    @Get
    public List<AccountingTransaction> listTransactions (@QueryValue long account, @QueryValue(defaultValue="0") int page)
    {
        return accounts.listTransactions(account,page);
    }

    @Get("/{transactionId}")
    public AccountingTransaction retrieveTransaction (@PathVariable long transactionId)
    {
        return accounts.retrieveTransaction(transactionId);
    }

    @Put("/{transactionId}")
    public AccountingTransaction updateTransaction (@PathVariable long transactionId, AccountingTransaction transaction)
    {
        accounts.updateTransaction(transaction);
        return transaction;
    }
}
