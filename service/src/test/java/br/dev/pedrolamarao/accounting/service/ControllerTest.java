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

import static io.micronaut.http.HttpRequest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@MicronautTest
public class ControllerTest
{
    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    AccountingAccountService service;

    @DisplayName("create account : failure")
    @Test
    public void createAccountFailure ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        doThrow(RuntimeException.class).when(service).createAccount(account);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                POST("/accounts",account),
                Argument.of(Stored.class,AccountingAccount.class)
            )
        );
        assertEquals(500,thrown.getStatus().getCode());
    }

    @DisplayName("delete account : failure")
    @Test
    public void deleteAccountFailure ()
    {
        final long accountId = 0;

        doThrow(RuntimeException.class).when(service).deleteAccount(accountId);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                DELETE("/accounts/"+accountId),
                Void.class
            )
        );
        assertEquals(500,thrown.getStatus().getCode());
    }

    @DisplayName("get account : failure")
    @Test
    public void getAccountFailure ()
    {
        final long accountId = 0;

        doThrow(RuntimeException.class).when(service).retrieveAccount(accountId);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                GET("/accounts/"+accountId),
                Argument.of(Stored.class,AccountingAccount.class)
            )
        );
        assertEquals(500,thrown.getStatus().getCode());
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
        assertEquals(500,thrown.getStatus().getCode());
    }

    @DisplayName("update account : failure")
    @Test
    public void updateAccountsFailure ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final long accountId = 0;

        doThrow(RuntimeException.class).when(service).updateAccount(0,account);

        final var thrown = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(
                PUT("/accounts/"+accountId,account),
                Argument.of(Stored.class,AccountingAccount.class)
            )
        );
        assertEquals(500,thrown.getStatus().getCode());
    }

    @MockBean(AccountingAccountService.class)
    public AccountingAccountService accountingAccountService ()
    {
        return mock(AccountingAccountService.class);
    }
}
