package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingAccountType;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static br.dev.pedrolamarao.accounting.model.AccountingTransactionType.CREDIT;
import static java.time.LocalDate.now;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

public abstract class ServiceTest
{
    public abstract AccountingService service();

    // accounts

    @DisplayName("create account")
    @Test
    public void createAccount ()
    {
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var accounts = service();
        final long accountId = accounts.createAccount(account);
        assertNotEquals(0,accountId);
    }

    @DisplayName("create account > delete account")
    @Test
    public void createAccountThenDeleteAccount ()
    {
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var accounts = service();
        final long accountId = accounts.createAccount(account);
        final var previous = accounts.deleteAccount(accountId);
        assertEquals( account.withId(accountId), previous );
    }

    @DisplayName("create account > delete account > get account")
    @Test
    public void createAccountThenDeleteAccountThenGetAccount ()
    {
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
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
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
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
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
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
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var accounts = service();
        final long accountId = accounts.createAccount(account);
        final var current = accounts.retrieveAccount(accountId);
        assertEquals( account.withId(accountId), current );
    }

    @DisplayName("create account > list accounts")
    @Test
    public void createAccountThenListAccounts ()
    {
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var accounts = service();
        final var accountId = accounts.createAccount(account);
        final var list = accounts.listAccount(0);
        assertEquals( account.withId(accountId), list.get(0) );
    }

    @DisplayName("create account > update account")
    @Test
    public void createAccountThenUpdateAccount ()
    {
        final var first = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var second = new AccountingAccount(-1, AccountingAccountType.ASSET,"account-2");
        final var accounts = service();
        final long accountId = accounts.createAccount(first);
        final var previous = accounts.updateAccount(accountId,second);
        assertEquals( first.withId(accountId), previous );
    }

    @DisplayName("create account > update account > get account")
    @Test
    public void createAccountThenUpdateAccountThenGetAccount ()
    {
        final var first = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var accounts = service();
        final long accountId = accounts.createAccount(first);
        final var second = new AccountingAccount(accountId, AccountingAccountType.ASSET,"account-2");
        accounts.updateAccount(accountId,second);
        final var current = accounts.retrieveAccount(accountId);
        assertEquals( second.withId(accountId), current );
    }

    @DisplayName("create account > update account > list accounts")
    @Test
    public void createAccountThenUpdateAccountThenListAccounts ()
    {
        final var first = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var accounts = service();
        final long accountId = accounts.createAccount(first);
        final var second = new AccountingAccount(accountId, AccountingAccountType.ASSET,"account-2");
        accounts.updateAccount(accountId,second);
        final var list = accounts.listAccount(0);
        assertEquals( second.withId(accountId), list.get(0) );
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
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var accounts = service();
        final var previous = accounts.updateAccount(accountId,account);
        assertNull(previous);
    }

    @DisplayName("update account (nonexistent) > get account")
    @Test
    public void updateAccountNonexistentThenGetAccount ()
    {
        final long accountId = 49;
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
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
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var accounts = service();
        accounts.updateAccount(accountId,account);
        final var list = accounts.listAccount(0);
        assertIterableEquals(emptyList(),list);
    }

    // transactions

    @DisplayName("create account > create transaction")
    @Test
    public void createAccountThenCreateTransaction ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transaction = new AccountingTransaction(-1, accountId, CREDIT, now(),0,"transaction");
        final long transactionId = service.createTransaction(transaction);
        assertNotEquals(0,transactionId);
    }

    @DisplayName("create account > create transaction > delete transaction")
    @Test
    public void createAccountThenCreateTransactionThenDeleteTransaction ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transaction = new AccountingTransaction(-1, accountId, CREDIT, now(),0,"transaction");
        final long transactionId = service.createTransaction(transaction);
        final var previous = service.deleteTransaction(transactionId);
        assertEquals( transaction.withId(transactionId), previous );
    }

    @DisplayName("create account > create transaction > delete transaction > get transaction")
    @Test
    public void createAccountThenCreateTransactionThenDeleteTransactionThenGetTransaction ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transaction = new AccountingTransaction(-1, accountId, CREDIT, now(),0,"transaction");
        final long transactionId = service.createTransaction(transaction);
        service.deleteTransaction(transactionId);
        final var current = service.retrieveTransaction(transactionId);
        assertNull(current);
    }

    @DisplayName("create account > create transaction > delete transaction > list transaction")
    @Test
    public void createAccountThenCreateTransactionThenDeleteTransactionThenListTransaction ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transaction = new AccountingTransaction(-1, accountId, CREDIT, now(),0,"transaction");
        final long transactionId = service.createTransaction(transaction);
        service.deleteTransaction(transactionId);
        final var transactions = service.listTransactions(accountId,0);
        assertIterableEquals(emptyList(),transactions);
    }

    @DisplayName("create account > create transaction > get transaction")
    @Test
    public void createAccountThenCreateTransactionThenGetTransaction ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transaction = new AccountingTransaction(-1, accountId, CREDIT, now(),0,"transaction");
        final long transactionId = service.createTransaction(transaction);
        final var current = service.retrieveTransaction(transactionId);
        assertEquals( transaction.withId(transactionId), current );
    }

    @DisplayName("create account > create transaction > list transaction")
    @Test
    public void createAccountThenCreateTransactionThenListTransaction ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transaction = new AccountingTransaction(-1, accountId, CREDIT, now(),0,"transaction");
        final long transactionId = service.createTransaction(transaction);
        final var transactions = service.listTransactions(accountId,0);
        assertIterableEquals( List.of(transaction.withId(transactionId)), transactions.stream().toList() );
    }

    @DisplayName("create account > create transaction > update transaction")
    @Test
    public void createAccountThenCreateTransactionThenUpdateTransaction ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transaction0 = new AccountingTransaction(-1, accountId, CREDIT,now(),0,"0");
        final long transactionId = service.createTransaction(transaction0);
        final var transaction1 = new AccountingTransaction(transactionId, accountId, CREDIT,now(),1,"1");
        final var previous = service.updateTransaction(transaction1);
        assertEquals( transaction0.withId(transactionId), previous );
    }

    @DisplayName("create account > create transaction > update transaction > get transaction")
    @Test
    public void createAccountThenCreateTransactionThenUpdateTransactionThenGetTransaction ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transaction0 = new AccountingTransaction(-1, accountId, CREDIT, now(),0,"0");
        final long transactionId = service.createTransaction(transaction0);
        final var transaction1 = new AccountingTransaction(transactionId, accountId, CREDIT, now(),1,"1");
        service.updateTransaction(transaction1);
        final var current = service.retrieveTransaction(transactionId);
        assertEquals( transaction1.withId(transactionId), current );
    }

    @DisplayName("create account > create transaction > update transaction > list transaction")
    @Test
    public void createAccountThenCreateTransactionThenUpdateTransactionThenListTransaction ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transaction0 = new AccountingTransaction(-1, accountId, CREDIT,now(),0,"0");
        final long transactionId = service.createTransaction(transaction0);
        final var transaction1 = new AccountingTransaction(transactionId, accountId, CREDIT,now(),1,"1");
        service.updateTransaction(transaction1);
        final var transactions = service.listTransactions(accountId,0);
        assertIterableEquals( List.of(transaction1.withId(transactionId)), transactions.stream().toList() );
    }

    @DisplayName("create account > delete transaction")
    @Test
    public void createAccountThenDeleteTransactionButNonexistent ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transaction = service.deleteTransaction(0);
        assertNull(transaction);
    }

    @DisplayName("create account > get transaction (transaction nonexistent)")
    @Test
    public void createAccountThenGetTransactionButNonexistent ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var accountId = service.createAccount(account);
        final var transaction = service.retrieveTransaction(0);
        assertNull(transaction);
    }

    @DisplayName("create account > list transactions")
    @Test
    public void createAccountThenListTransactions ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final long accountId = service.createAccount(account);
        final var transactions = service.listTransactions(accountId,0);
        assertIterableEquals(emptyList(),transactions);
    }

    @DisplayName("create account > update transaction (transaction nonexistent)")
    @Test
    public void createAccountThenUpdateTransactionButNonexistent ()
    {
        final var service = service();
        final var account = new AccountingAccount(-1, AccountingAccountType.ASSET,"account");
        final var accountId = service.createAccount(account);
        final var transaction = new AccountingTransaction(-1, accountId, CREDIT, now(),0,"transaction");
        final var previous = service.updateTransaction(transaction);
        assertNull(previous);
    }

    @DisplayName("create transaction (account nonexistent)")
    @Test
    public void createTransactionButAccountNonexistent ()
    {
        final var service = service();
        final var account = new AccountingAccount(999, AccountingAccountType.ASSET,"account");
        final var transaction = new AccountingTransaction(-1, 999, CREDIT, now(),0,"transaction");
        final long transactionId = service.createTransaction(transaction);
        assertEquals(0,transactionId);
    }

    @DisplayName("delete transaction (account nonexistent)")
    @Test
    public void deleteTransactionButAccountNonexistent ()
    {
        final var service = service();
        final var transaction = service.deleteTransaction(0);
        assertNull(transaction);
    }

    @DisplayName("get transaction (account nonexistent)")
    @Test
    public void getTransactionButAccountNonexistent ()
    {
        final var service = service();
        final var transaction = service.retrieveTransaction(0);
        assertNull(transaction);
    }

    @DisplayName("list transactions (account nonexistent)")
    @Test
    public void listTransactionsButAccountNonexistent ()
    {
        final var service = service();
        final var transactions = service.listTransactions(0,0);
        assertIterableEquals(emptyList(),transactions);
    }

    @DisplayName("update transaction (account nonexistent)")
    @Test
    public void updateTransactionsButAccountNonexistent ()
    {
        final var service = service();
        final var account = new AccountingAccount(0, AccountingAccountType.ASSET,"account");
        final var transaction = new AccountingTransaction(-1, 0, CREDIT, now(),0,"transaction");
        final var previous = service.updateTransaction(transaction);
        assertNull(previous);
    }
}
