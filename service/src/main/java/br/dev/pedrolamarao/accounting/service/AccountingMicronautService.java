package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

import java.util.List;

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
        return 0;
    }

    @Override
    public AccountingAccount deleteAccount (long id)
    {
        return null;
    }

    @Override
    public List<Listed<AccountingAccount>> listAccount (int page)
    {
        return null;
    }

    @Override
    public AccountingAccount retrieveAccount (long id)
    {
        return null;
    }

    @Override
    public AccountingAccount updateAccount (long id, AccountingAccount account)
    {
        return null;
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
