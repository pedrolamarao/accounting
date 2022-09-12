package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class AccountingAccountServiceMemory implements AccountingAccountService
{
    private final HashMap<Long,AccountingAccount> accounts = new HashMap<>();

    private final AtomicInteger counter = new AtomicInteger(1);

    private final HashMap<Long,HashMap<Long,AccountingTransaction>> transactions = new HashMap<>();

    @Override
    public long createAccount (AccountingAccount account)
    {
        final long id = counter.incrementAndGet();
        accounts.put(id,account);
        return id;
    }

    @Override
    public AccountingAccount deleteAccount (long id)
    {
        return accounts.remove(id);
    }

    @Override
    public List<Listed<AccountingAccount>> listAccount (int page)
    {
        final var list = new ArrayList<Listed<AccountingAccount>>(accounts.size());
        accounts.forEach((id,account) -> list.add(new Listed<>(id,account)));
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
    public long createTransaction (long accountId, AccountingTransaction transaction)
    {
        if (! accounts.containsKey(accountId)) return 0;
        final long id = counter.incrementAndGet();
        transactions.computeIfAbsent(accountId,(__) -> new HashMap<>()).put(id,transaction);
        return id;
    }

    @Override
    public AccountingTransaction deleteTransaction (long account, long transaction)
    {
        return transactions.computeIfAbsent(account,(__) -> new HashMap<>()).remove(transaction);
    }

    @Override
    public List<Listed<AccountingTransaction>> listTransactions (long account, int page)
    {
        final var map = transactions.computeIfAbsent(account,(__) -> new HashMap<>());
        final var list = new ArrayList<Listed<AccountingTransaction>>(map.size());
        map.forEach((id,value) -> list.add(new Listed<>(id,value)));
        return list;
    }

    @Override
    public AccountingTransaction retrieveTransaction (long account, long transaction)
    {
        return transactions.computeIfAbsent(account,(__) -> new HashMap<>()).get(transaction);
    }

    @Override
    public AccountingTransaction updateTransaction (long account, long transaction, AccountingTransaction value)
    {
        return transactions.computeIfAbsent(account,(__) -> new HashMap<>()).put(transaction,value);
    }
}
