package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.StreamSupport;

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
    public long createTransaction (long account, AccountingTransaction transaction)
    {
        return 0;
    }

    @Override
    public AccountingTransaction deleteTransaction (long account, long transaction)
    {
        return null;
    }

    @Override
    public List<Listed<AccountingTransaction>> listTransactions (long account, int page)
    {
        return null;
    }

    @Override
    public AccountingTransaction retrieveTransaction (long account, long transaction)
    {
        return null;
    }

    @Override
    public AccountingTransaction updateTransaction (long account, long transaction, AccountingTransaction value)
    {
        return null;
    }
}
