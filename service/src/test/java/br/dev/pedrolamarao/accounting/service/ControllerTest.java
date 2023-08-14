package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.core.type.Argument;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static br.dev.pedrolamarao.accounting.model.AccountingAccountType.ASSET;
import static br.dev.pedrolamarao.accounting.model.AccountingTransactionType.CREDIT;
import static io.micronaut.http.HttpRequest.*;
import static io.micronaut.http.HttpStatus.*;
import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@MicronautTest
public class ControllerTest
{
    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    AccountingService service;

    // accounts

    @DisplayName("create account")
    @Test
    public void createAccount ()
    {
        final var accountId = 49L;
        final var account = new AccountingAccount(-1, ASSET,"name");

        when( service.createAccount(account) ).thenReturn( accountId );

        final var response = client.toBlocking().exchange(
            POST("/accounts",account),
            Argument.of(AccountingAccount.class)
        );
        assertEquals( CREATED, response.getStatus() );
        assertEquals( account.type(), response.body().type() );
        assertEquals( account.name(), response.body().name() );
    }

    @DisplayName("create account : failure")
    @Test
    public void createAccountFailure ()
    {
        final var account = new AccountingAccount(-1, ASSET,"name");

        doThrow( RuntimeException.class ).when(service).createAccount(account);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                POST("/accounts",account),
                Argument.of(AccountingAccount.class)
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("delete account")
    @Test
    public void deleteAccount ()
    {
        final var accountId = 49L;

        when( service.deleteAccount(accountId) ).thenReturn( null );

        final var response = client.toBlocking().exchange(
            DELETE("/accounts/"+accountId),
            Argument.of(AccountingAccount.class)
        );
        assertEquals( OK, response.getStatus() );
    }

    @DisplayName("delete account : failure")
    @Test
    public void deleteAccountFailure ()
    {
        final long accountId = 0;

        doThrow( RuntimeException.class ).when(service).deleteAccount(accountId);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                DELETE("/accounts/"+accountId),
                Void.class
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("get account")
    @Test
    public void getAccount ()
    {
        final var accountId = 49L;
        final var account = new AccountingAccount(-1, ASSET,"name");

        when( service.retrieveAccount(accountId) ).thenReturn( account );

        final var response = client.toBlocking().exchange(
            GET("/accounts/"+accountId),
            Argument.of(AccountingAccount.class)
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( account, response.body() );
    }

    @DisplayName("get account : failure")
    @Test
    public void getAccountFailure ()
    {
        final long accountId = 0;

        doThrow( RuntimeException.class ).when(service).retrieveAccount(accountId);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                GET("/accounts/"+accountId),
                Argument.of(AccountingAccount.class)
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("get account : nonexistent")
    @Test
    public void getAccountNonexistent ()
    {
        final var accountId = 49L;

        when( service.retrieveAccount(accountId) ).thenReturn( null );

        final var thrown = assertThrows(
                HttpClientResponseException.class,
                () -> client.toBlocking().exchange(
                    GET("/accounts/"+accountId),
                    Argument.of(AccountingAccount.class)
                )
        );
        assertEquals( NOT_FOUND, thrown.getStatus() );
    }

    @DisplayName("list accounts")
    @Test
    public void listAccounts ()
    {
        final var account = new AccountingAccount(-1, ASSET,"name");

        when( service.listAccount(0) ).thenReturn( List.of( account ) );

        final var response = client.toBlocking().exchange(
            GET("/accounts/"),
            Argument.of(List.class,Argument.of(AccountingAccount.class))
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( account, response.body().get(0) );
    }

    @DisplayName("list accounts : page")
    @Test
    public void listAccountsPage ()
    {
        final var account = new AccountingAccount(-1, ASSET,"name");
        final var pageId = 49;

        when( service.listAccount(pageId) ).thenReturn( List.of( account ) );

        final var response = client.toBlocking().exchange(
            GET("/accounts/?page=49"),
            Argument.of(List.class,Argument.of(AccountingAccount.class))
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( account, response.body().get(0) );
    }

    @DisplayName("list account : failure")
    @Test
    public void listAccountsFailure ()
    {
        doThrow(RuntimeException.class).when(service).listAccount(0);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                GET("/accounts/"),
                Argument.of(AccountingAccount.class)
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("update account")
    @Test
    public void updateAccount ()
    {
        final long accountId = 49;
        final var account = new AccountingAccount(-1, ASSET,"name");

        when( service.updateAccount(accountId,account) ).thenReturn( account );

        final var response = client.toBlocking().exchange(
            PUT("/accounts/"+accountId,account),
            Argument.of(AccountingAccount.class)
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( account, response.body() );
    }

    @DisplayName("update account : failure")
    @Test
    public void updateAccountsFailure ()
    {
        final long accountId = 0;
        final var account = new AccountingAccount(-1, ASSET,"name");

        doThrow( RuntimeException.class ).when(service).updateAccount(0,account);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                PUT("/accounts/"+accountId,account),
                Argument.of(AccountingAccount.class)
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("update account : nonexistent")
    @Test
    public void updateAccountsNonexistent ()
    {
        final long accountId = 0;
        final var account = new AccountingAccount(-1, ASSET,"name");

        when( service.updateAccount(accountId,account) ).thenReturn( null );

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                PUT("/accounts/"+accountId,account),
                Argument.of(AccountingAccount.class)
            )
        );
        assertEquals( NOT_FOUND, thrown.getStatus() );
    }

    // transactions

    @DisplayName("create transaction")
    @Test
    public void createTransaction ()
    {
        final var accountId = 31L;
        final var account = new AccountingAccount(accountId, ASSET,"account");
        final var transactionId = 49L;
        final var transaction = new AccountingTransaction(transactionId, accountId, CREDIT,now(),0,"transaction");

        when( service.createTransaction(transaction) ).thenReturn( transactionId );

        final var response = client.toBlocking().exchange(
            POST("/accounts/"+accountId+"/transactions",transaction),
            Argument.of(AccountingTransaction.class)
        );
        assertEquals( CREATED, response.getStatus() );
        assertEquals( transaction, response.body() );
    }

    @DisplayName("create transaction : failure")
    @Test
    public void createTransactionFailure ()
    {
        final var accountId = 31L;
        final var account = new AccountingAccount(accountId, ASSET,"account");
        final var transactionId = 49L;
        final var transaction = new AccountingTransaction(transactionId, accountId, CREDIT,now(),0,"transaction");

        doThrow( RuntimeException.class ).when(service).createTransaction(transaction);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                POST("/accounts/"+accountId+"/transactions",transaction),
                Argument.of(AccountingTransaction.class)
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("delete transaction")
    @Test
    public void deleteTransaction ()
    {
        final var accountId = 31L;
        final var account = new AccountingAccount(accountId, ASSET,"account");
        final var transactionId = 49L;
        final var transaction = new AccountingTransaction(transactionId, accountId, CREDIT,now(),0,"transaction");

        when( service.deleteTransaction(transactionId) ).thenReturn( transaction );

        final var response = client.toBlocking().exchange(
            DELETE("/accounts/"+accountId+"/transactions/"+transactionId),
            Void.class
        );
        assertEquals( OK, response.getStatus() );
    }

    @DisplayName("delete transaction : failure")
    @Test
    public void deleteTransactionFailure ()
    {
        final var accountId = 31L;
        final var transactionId = 49L;

        doThrow( RuntimeException.class).when(service).deleteTransaction(transactionId);
        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                DELETE("/accounts/"+accountId+"/transactions/"+transactionId),
                Void.class
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("get transaction")
    @Test
    public void getTransaction ()
    {
        final var accountId = 31L;
        final var account = new AccountingAccount(accountId, ASSET,"account");
        final var transactionId = 49L;
        final var transaction = new AccountingTransaction(transactionId, accountId, CREDIT,now(),0,"transaction");

        when( service.retrieveTransaction(transactionId) ).thenReturn( transaction );

        final var response = client.toBlocking().exchange(
            GET("/accounts/"+accountId+"/transactions/"+transactionId),
            Argument.of(AccountingTransaction.class)
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( transaction, response.body() );
    }

    @DisplayName("get transaction : failure")
    @Test
    public void getTransactionFailure ()
    {
        final var accountId = 31L;
        final var transactionId = 49L;

        doThrow( RuntimeException.class).when(service).retrieveTransaction(transactionId);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                GET("/accounts/"+accountId+"/transactions/"+transactionId),
                Argument.of(AccountingTransaction.class)
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("list transactions")
    @Test
    public void listTransactions ()
    {
        final var accountId = 31L;
        final var account = new AccountingAccount(accountId, ASSET,"account");
        final var transactionId = 49L;
        final var transaction = new AccountingTransaction(transactionId, accountId, CREDIT,now(),0,"transaction");

        when( service.listTransactions(accountId,0) ).thenReturn( List.of( transaction ) );

        final var response = client.toBlocking().exchange(
            GET("/accounts/"+accountId+"/transactions"),
            Argument.of(List.class,Argument.of(AccountingTransaction.class))
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( transaction, response.body().get(0) );
    }

    @DisplayName("list transactions : page")
    @Test
    public void listTransactionsPage ()
    {
        final var accountId = 31L;
        final var account = new AccountingAccount(accountId, ASSET,"account");
        final var page = 27;
        final var transactionId = 49L;
        final var transaction = new AccountingTransaction(transactionId, accountId, CREDIT,now(),0,"transaction");

        when( service.listTransactions(accountId,page) ).thenReturn( List.of( transaction ) );

        final var response = client.toBlocking().exchange(
            GET("/accounts/"+accountId+"/transactions?page="+page),
            Argument.of(List.class,Argument.of(AccountingTransaction.class))
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( transaction, response.body().get(0) );
    }

    @DisplayName("list transactions : failure")
    @Test
    public void listTransactionsFailure ()
    {
        final var accountId = 31L;
        final var account = new AccountingAccount(accountId, ASSET,"account");
        final var page = 27;
        final var transactionId = 49L;
        final var transaction = new AccountingTransaction(transactionId, accountId, CREDIT,now(),0,"transaction");

        doThrow( RuntimeException.class ).when( service ).listTransactions(accountId,page);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                GET("/accounts/"+accountId+"/transactions?page="+page),
                Argument.of(List.class,Argument.of(AccountingTransaction.class))
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("update transaction")
    @Test
    public void updateTransaction ()
    {
        final var accountId = 31L;
        final var account = new AccountingAccount(accountId, ASSET,"account");
        final var transactionId = 49L;
        final var transaction0 = new AccountingTransaction(transactionId, accountId, CREDIT,now(),0,"description");
        final var transaction1 = new AccountingTransaction(transactionId, accountId, CREDIT,now(),1,"description");

        when( service.updateTransaction(transaction1) ).thenReturn( transaction0 );

        final var response = client.toBlocking().exchange(
            PUT("/accounts/"+accountId+"/transactions/"+transactionId,transaction1),
            Argument.of(AccountingTransaction.class)
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( transaction1, response.body() );
    }

    @DisplayName("update transaction : failure")
    @Test
    public void updateTransactionFailure ()
    {
        final var accountId = 31L;
        final var account = new AccountingAccount(accountId, ASSET,"account");
        final var transactionId = 49L;
        final var transaction = new AccountingTransaction(transactionId, accountId, CREDIT,now(),0,"transaction");

        doThrow( RuntimeException.class).when( service ).updateTransaction(transaction);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                PUT("/accounts/"+accountId+"/transactions/"+transactionId,transaction),
                Argument.of(AccountingTransaction.class)
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    // support

    @MockBean(AccountingService.class)
    public AccountingService accountingAccountService ()
    {
        return mock(AccountingService.class);
    }
}
