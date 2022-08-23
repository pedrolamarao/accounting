package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingAccountType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

public abstract class ServiceTest
{
    public abstract AccountingAccountService service();

    @DisplayName("create account")
    @Test
    public void createAccount ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        final long accountId = accounts.createAccount(account);
        assertNotEquals(0,accountId);
    }

    @DisplayName("create account > delete account")
    @Test
    public void createAccountThenDeleteAccount ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        final long accountId = accounts.createAccount(account);
        final var previous = accounts.deleteAccount(accountId);
        assertEquals(account,previous);
    }

    @DisplayName("create account > delete account > get account")
    @Test
    public void createAccountThenDeleteAccountThenGetAccount ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        final long accountId = accounts.createAccount(account);
        accounts.deleteAccount(accountId);
        final var current = accounts.retrieveAccount(accountId);
        assertNull(current);
    }

    @DisplayName("create account > delete account > list accounts")
    @Test
    public void createAccountThenDeleteAccountThenListAccounts ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        final long accountId = accounts.createAccount(account);
        accounts.deleteAccount(accountId);
        final var list = accounts.listAccount(0);
        assertIterableEquals(emptyList(),list);
    }

    @DisplayName("create account > delete account > update accounts")
    @Test
    public void createAccountThenDeleteAccountThenUpdateAccount ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        final long accountId = accounts.createAccount(account);
        accounts.deleteAccount(accountId);
        final var previous = accounts.updateAccount(accountId,account);
        assertNull(previous);
    }

    @DisplayName("create account > get account")
    @Test
    public void createAccountThenGetAccount ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        final long accountId = accounts.createAccount(account);
        final var current = accounts.retrieveAccount(accountId);
        assertEquals(account,current);
    }
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        final long accountId = accounts.createAccount(account);
        accounts.deleteAccount(accountId);
        final var current = accounts.retrieveAccount(accountId);
        assertNull(current);
    }

    @DisplayName("create account > list accounts")
    @Test
    public void createAccountThenListAccounts ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        accounts.createAccount(account);
        final var list = accounts.listAccount(0);
        assertEquals( account, list.get(0).value() );
    }

    @DisplayName("create account > update account")
    @Test
    public void createAccountThenUpdateAccount ()
    {
        final var first = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var second = new AccountingAccount(AccountingAccountType.ASSET,"NAME");
        final var accounts = service();
        final long accountId = accounts.createAccount(first);
        final var previous = accounts.updateAccount(accountId,second);
        assertEquals(first,previous);
    }

    @DisplayName("create account > update account > get account")
    @Test
    public void createAccountThenUpdateAccountThenGetAccount ()
    {
        final var first = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var second = new AccountingAccount(AccountingAccountType.ASSET,"NAME");
        final var accounts = service();
        final long accountId = accounts.createAccount(first);
        accounts.updateAccount(accountId,second);
        final var current = accounts.retrieveAccount(accountId);
        assertEquals(second,current);
    }

    @DisplayName("create account > update account > list accounts")
    @Test
    public void createAccountThenUpdateAccountThenListAccounts ()
    {
        final var first = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var second = new AccountingAccount(AccountingAccountType.ASSET,"NAME");
        final var accounts = service();
        final long accountId = accounts.createAccount(first);
        accounts.updateAccount(accountId,second);
        final var list = accounts.listAccount(0);
        assertEquals(second,list.get(0).value());
    }

    @DisplayName("delete account (nonexistent)")
    @Test
    public void deleteAccountNonexistent ()
    {
        final long accountId = 49;
        final var accounts = service();
        final var previous = accounts.deleteAccount(accountId);
        assertNull(previous);
    }

    @DisplayName("get account (nonexistent)")
    @Test
    public void getAccountNonexistent ()
    {
        final long accountId = 49;
        final var accounts = service();
        final var current = accounts.retrieveAccount(accountId);
        assertNull(current);
    }

    @DisplayName("list accounts")
    @Test
    public void listAccounts ()
    {
        final var accounts = service();
        final var list = accounts.listAccount(0);
        assertIterableEquals(emptyList(),list);
    }

    @DisplayName("update account (nonexistent)")
    @Test
    public void updateAccountNonexistent ()
    {
        final long accountId = 49;
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        final var previous = accounts.updateAccount(accountId,account);
        assertNull(previous);
    }

    @DisplayName("update account (nonexistent) > get account")
    @Test
    public void updateAccountNonexistentThenGetAccount ()
    {
        final long accountId = 49;
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        final var previous = accounts.updateAccount(accountId,account);
        assertNull(previous);
        final var current = accounts.retrieveAccount(accountId);
        assertNull(current);
    }

    @DisplayName("update account (nonexistent) > list accounts")
    @Test
    public void updateAccountNonexistentThenListAccount ()
    {
        final long accountId = 49;
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var accounts = service();
        accounts.updateAccount(accountId,account);
        final var list = accounts.listAccount(0);
        assertIterableEquals(emptyList(),list);
    }
}
