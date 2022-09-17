package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyList;

@Primary
@Singleton
class AccountingMicronautService implements AccountingService
{
    private final AccountRepository accounts;

    private final TransactionRepository transactions;

    AccountingMicronautService (AccountRepository accounts, TransactionRepository transactions)
    {
        this.accounts = accounts;
        this.transactions = transactions;
    }

    @Override
    public long createAccount (AccountingAccount account)
    {
        return accounts.save(account).id();
    }

    @Override
    public AccountingAccount deleteAccount (long id)
    {
        final var account = accounts.findById(id);
        accounts.deleteById(id);
        return account.orElse(null);
    }

    @Override
    public List<Listed<AccountingAccount>> listAccount (int page)
    {
        return StreamSupport.stream(accounts.findAll().spliterator(),false).map(it -> new Listed<>(it.id(),it)).toList();
    }

    @Override
    public AccountingAccount retrieveAccount (long id)
    {
        return accounts.findById(id).orElse(null);
    }

    @Override
    public AccountingAccount updateAccount (long id, AccountingAccount account)
    {
        final var previous = accounts.findById(id).orElse(null);
        if (previous != null) accounts.update(account);
        return previous;
    }

    @Override
    public long createTransaction (long accountId, AccountingTransaction transaction)
    {
        final var account = accounts.findById(accountId).orElse(null);
        if (account == null) return 0;
        final var new_ = new AccountingTransaction(transaction.id(),account,transaction.type(),transaction.date(),transaction.moneys(),transaction.description());
        return transactions.save(new_).id();
    }

    @Override
    public AccountingTransaction deleteTransaction (long accountId, long transaction)
    {
        final var account = accounts.findById(accountId).orElse(null);
        if (account == null) return null;
        final var previous = transactions.findById(transaction);
        transactions.deleteById(transaction);
        return previous.map(it -> it.withAccount(account)).orElse(null);
    }

    @Override
    public List<Listed<AccountingTransaction>> listTransactions (long accountId, int page)
    {
        final var account = accounts.findById(accountId).orElse(null);
        if (account == null) return emptyList();
        return StreamSupport.stream(transactions.findAll().spliterator(),false).map(it -> new Listed<>(it.id(),it.withAccount(account))).toList();
    }

    @Override
    public AccountingTransaction retrieveTransaction (long accountId, long transactionId)
    {
        final var account = accounts.findById(accountId).orElse(null);
        if (account == null) return null;
        return transactions.findById(transactionId).map(it -> it.withAccount(account)).orElse(null);
    }

    @Override
    public AccountingTransaction updateTransaction (long accountId, long transactionId, AccountingTransaction value)
    {
        final var account = accounts.findById(accountId).orElse(null);
        if (account == null) return null;
        final var previous = transactions.findById(transactionId);
        transactions.update(value.withId(transactionId));
        return previous.map(it -> it.withAccount(account)).orElse(null);
    }
}
