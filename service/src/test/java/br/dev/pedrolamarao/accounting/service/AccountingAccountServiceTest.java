package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingAccountType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountingAccountServiceTest
{
    @Test
    public void create ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = new AccountingAccountServiceMemory();
        final long id = accounts.createAccount(account);
        final var list = accounts.listAccount(0);
        assertEquals(id,list.get(0).id());
        assertEquals(account,list.get(0).value());
    }

    @Test
    public void list ()
    {
        final var accounts = new AccountingAccountServiceMemory();
        final var list = accounts.listAccount(0);
        assertNotNull(list);
        assertEquals(0,list.size());
    }

    @Test
    public void update ()
    {
        final var first = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var second = new AccountingAccount(AccountingAccountType.ASSET,"NAME");
        final var accounts = new AccountingAccountServiceMemory();
        final long id = accounts.createAccount(first);
        assertEquals(first,accounts.retrieveAccount(id));
        accounts.updateAccount(id,second);
        assertEquals(second,accounts.retrieveAccount(id));
    }
}
