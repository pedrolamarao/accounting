// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller("/transactions")
class TransactionsController
{
    private final TransactionRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    TransactionsController (TransactionRepository repository)
    {
        this.repository = repository;
    }

    @Post
    @Status(HttpStatus.CREATED)
    public List<AccountingTransaction> createTransaction (List<AccountingTransaction> transactions)
    {
        return transactions.stream().map(repository::save).toList();
    }

    @Delete("/{transactionId}")
    public void deleteTransaction (@PathVariable long transactionId)
    {
        repository.deleteById(transactionId);
    }

    @Get
    public Iterable<AccountingTransaction> listTransactions (@QueryValue long account)
    {
        return repository.findAll();
    }

    @Get("/{transactionId}")
    public AccountingTransaction retrieveTransaction (@PathVariable long transactionId)
    {
        return repository.findById(transactionId).orElseThrow();
    }

    @Put("/{transactionId}")
    public AccountingTransaction updateTransaction (@PathVariable long transactionId, AccountingTransaction transaction)
    {
        assert transaction.id() == transactionId;
        return repository.update(transaction);
    }
}
