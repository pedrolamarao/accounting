package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class AccountingMemoryService implements AccountingService
{
    private final HashMap<Long,AccountingAccount> accounts = new HashMap<>();

    private final AtomicInteger counter = new AtomicInteger(1);

    private final HashMap<Long,AccountingTransaction> transactions = new HashMap<>();

    @Override
    public long createAccount (AccountingAccount account)
    {
        final long id = counter.incrementAndGet();
        accounts.put(id,account.withId(id));
        return id;
    }

    @Override
    public AccountingAccount deleteAccount (long id)
    {
        return accounts.remove(id);
    }

    @Override
    public List<AccountingAccount> listAccount (int page)
    {
        final var list = new ArrayList<AccountingAccount>(accounts.size());
        accounts.forEach((id,account) -> list.add(account));
        return list;
    }

    @Override
    public AccountingAccount retrieveAccount (long id)
    {
        return accounts.get(id);
    }

    @Override
    public AccountingAccount updateAccount (long id, AccountingAccount account)
    {
        if (! accounts.containsKey(id)) return null;
        else return accounts.put(id,account);
    }

    @Override
    public long createTransaction ( AccountingTransaction transaction)
    {
        final long accountId = transaction.account();
        if (! accounts.containsKey(accountId)) return 0;
        final long transactionId = counter.incrementAndGet();
        final var new_ = transaction.withId(transactionId);
        transactions.put(transactionId,new_);
        return transactionId;
    }

    @Override
    public AccountingTransaction deleteTransaction (long transaction)
    {
        return transactions.remove(transaction);
    }

    @Override
    public List<AccountingTransaction> listTransactions (long account, int page)
    {
        return transactions.values().stream().filter(it -> it.account() == account).toList();
    }

    @Override
    public AccountingTransaction retrieveTransaction (long transaction)
    {
        return transactions.get(transaction);
    }

    @Override
    public AccountingTransaction updateTransaction (AccountingTransaction value)
    {
        if (!transactions.containsKey(value.id())) return null;
        return transactions.put(value.id(),value);
    }
}
