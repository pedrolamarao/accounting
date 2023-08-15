// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
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

    @Consumes(MediaType.APPLICATION_JSON)
    @Post
    @Status(HttpStatus.CREATED)
    public List<AccountingTransaction> createFromJson (@Body List<AccountingTransaction> transactions)
    {
        return transactions.stream().map(repository::save).toList();
    }

    @Consumes(MediaType.TEXT_CSV)
    @Post
    @Status(HttpStatus.CREATED)
    public List<AccountingTransaction> createFromCsv (@Body String csv)
    {
        logger.debug("{}",csv);
        throw new HttpStatusException(HttpStatus.I_AM_A_TEAPOT,"not yet");
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Delete("/{transactionId}")
    public void deleteTransaction (@PathVariable long transactionId)
    {
        repository.deleteById(transactionId);
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Get
    public Iterable<AccountingTransaction> listTransactions (@QueryValue long account)
    {
        return repository.findAll();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Get("/{transactionId}")
    public AccountingTransaction retrieveTransaction (@PathVariable long transactionId)
    {
        return repository.findById(transactionId).orElseThrow();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Put("/{transactionId}")
    public AccountingTransaction updateTransaction (@PathVariable long transactionId, @Body AccountingTransaction transaction)
    {
        assert transaction.id() == transactionId;
        return repository.update(transaction);
    }
}
