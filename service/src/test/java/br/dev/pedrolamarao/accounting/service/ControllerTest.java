package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingAccountType;
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

import static io.micronaut.http.HttpRequest.*;
import static io.micronaut.http.HttpStatus.*;
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
    AccountingAccountService service;

    @DisplayName("create account")
    @Test
    public void createAccount ()
    {
        final var accountId = 49L;
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        when( service.createAccount(account) ).thenReturn( accountId );

        final var response = client.toBlocking().exchange(
            POST("/accounts",account),
            Argument.of(Stored.class,AccountingAccount.class)
        );
        assertEquals( CREATED, response.getStatus() );
        assertEquals( account, response.body().value() );
    }

    @DisplayName("create account : failure")
    @Test
    public void createAccountFailure ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        doThrow( RuntimeException.class ).when(service).createAccount(account);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                POST("/accounts",account),
                Argument.of(Stored.class,AccountingAccount.class)
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("delete account")
    @Test
    public void deleteAccount ()
    {
        final var accountId = 49L;

        doNothing().when(service).deleteAccount(accountId);

        final var response = client.toBlocking().exchange(
            DELETE("/accounts/"+accountId),
            Argument.of(Stored.class,AccountingAccount.class)
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
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        when( service.retrieveAccount(accountId) ).thenReturn( account );

        final var response = client.toBlocking().exchange(
            GET("/accounts/"+accountId),
            Argument.of(Stored.class,AccountingAccount.class)
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( account, response.body().value() );
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
                Argument.of(Stored.class,AccountingAccount.class)
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
                        Argument.of(Stored.class,AccountingAccount.class)
                )
        );
        assertEquals( NOT_FOUND, thrown.getStatus() );
    }

    @DisplayName("list accounts")
    @Test
    public void listAccounts ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        when( service.listAccount(0) ).thenReturn( List.of( new Listed<>(0,account) ) );

        final var response = client.toBlocking().exchange(
            GET("/accounts/"),
            Argument.of(Paged.class,Argument.of(Stored.class,AccountingAccount.class))
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( account, ((Stored<?>) response.body().values().get(0)).value() );
    }

    @DisplayName("list accounts : page")
    @Test
    public void listAccountsPage ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var pageId = 49;

        when( service.listAccount(pageId) ).thenReturn( List.of( new Listed<>(pageId,account) ) );

        final var response = client.toBlocking().exchange(
            GET("/accounts/?page=49"),
            Argument.of(Paged.class,Argument.of(Stored.class,AccountingAccount.class))
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( account, ((Stored<?>) response.body().values().get(0)).value() );
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
                Argument.of(Stored.class,AccountingAccount.class)
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("update account")
    @Test
    public void updateAccount ()
    {
        final long accountId = 49;
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        when( service.updateAccount(accountId,account) ).thenReturn( account );

        final var response = client.toBlocking().exchange(
            PUT("/accounts/"+accountId,account),
            Argument.of(Stored.class,AccountingAccount.class)
        );
        assertEquals( OK, response.getStatus() );
        assertEquals( account, response.body().value() );
    }

    @DisplayName("update account : failure")
    @Test
    public void updateAccountsFailure ()
    {
        final long accountId = 0;
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        doThrow( RuntimeException.class ).when(service).updateAccount(0,account);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                PUT("/accounts/"+accountId,account),
                Argument.of(Stored.class,AccountingAccount.class)
            )
        );
        assertEquals( INTERNAL_SERVER_ERROR, thrown.getStatus() );
    }

    @DisplayName("update account : nonexistent")
    @Test
    public void updateAccountsNonexistent ()
    {
        final long accountId = 0;
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        when( service.updateAccount(accountId,account) ).thenReturn( null );

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                PUT("/accounts/"+accountId,account),
                Argument.of(Stored.class,AccountingAccount.class)
            )
        );
        assertEquals( NOT_FOUND, thrown.getStatus() );
    }

    @MockBean(AccountingAccountService.class)
    public AccountingAccountService accountingAccountService ()
    {
        return mock(AccountingAccountService.class);
    }
}
