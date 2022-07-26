package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class AccountingAccountServiceMemory implements AccountingAccountService
{
    private final HashMap<Long,AccountingAccount> accounts = new HashMap<>();

    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public long create (AccountingAccount account)
    {
        final long id = counter.incrementAndGet();
        accounts.put(id,account);
        return id;
    }

    @Override
    public void delete (long id)
    {
        accounts.remove(id);
    }

    @Override
    public List<Listed<AccountingAccount>> list (int page)
    {
        final var list = new ArrayList<Listed<AccountingAccount>>(accounts.size());
        accounts.forEach((id,account) -> list.add(new Listed<>(id,account)));
        return list;
    }

    @Override
    public AccountingAccount retrieve (long id)
    {
        return accounts.get(id);
    }

    @Override
    public void update (long id, AccountingAccount account)
    {
        accounts.put(id,account);
    }
}
