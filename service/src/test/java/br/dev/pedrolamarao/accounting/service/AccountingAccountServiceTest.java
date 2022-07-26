package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountingAccountServiceTest
{
    @Test
    public void create ()
    {
        final var account = new AccountingAccount("description");
        final var accounts = new AccountingAccountServiceMemory();
        final long id = accounts.create(account);
        final var list = accounts.list(0);
        assertEquals(id,list.get(0).id());
        assertEquals(account,list.get(0).value());
    }

    @Test
    public void list ()
    {
        final var accounts = new AccountingAccountServiceMemory();
        final var list = accounts.list(0);
        assertNotNull(list);
        assertEquals(0,list.size());
    }

    @Test
    public void update ()
    {
        final var first = new AccountingAccount("description");
        final var second = new AccountingAccount("DESCRIPTION");
        final var accounts = new AccountingAccountServiceMemory();
        final long id = accounts.create(first);
        assertEquals(first,accounts.retrieve(id));
        accounts.update(id,second);
        assertEquals(second,accounts.retrieve(id));
    }
}
