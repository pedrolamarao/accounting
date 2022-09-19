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
    public List<AccountingAccount> listAccount (int page)
    {
        return StreamSupport.stream(accounts.findAll().spliterator(),false).toList();
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
    public long createTransaction (AccountingTransaction transaction)
    {
        if (! accounts.existsById(transaction.account())) return 0;
        return transactions.save(transaction).id();
    }

    @Override
    public AccountingTransaction deleteTransaction (long transaction)
    {
        final var previous = transactions.findById(transaction);
        transactions.deleteById(transaction);
        return previous.orElse(null);
    }

    @Override
    public List<AccountingTransaction> listTransactions (long accountId, int page)
    {
        final var account = accounts.findById(accountId).orElse(null);
        if (account == null) return emptyList();
        return StreamSupport.stream(transactions.findAll().spliterator(),false).toList();
    }

    @Override
    public AccountingTransaction retrieveTransaction (long transactionId)
    {
        return transactions.findById(transactionId).orElse(null);
    }

    @Override
    public AccountingTransaction updateTransaction (AccountingTransaction value)
    {
        final var previous = transactions.findById(value.id());
        transactions.update(value);
        return previous.orElse(null);
    }
}
