package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class AccountingAccountControllerTest
{
    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void listAccounts ()
    {
        final var response = client.toBlocking().retrieve(HttpRequest.GET("/accounts"));
        assertNotNull(response);
    }

    @Test
    void updateAccount ()
    {
        final var account = new AccountingAccount("bar");
        final var response = client.toBlocking().retrieve(
            HttpRequest.PUT("/accounts/foo",account),
            AccountingAccount.class
        );
        assertEquals(account,response);
    }

    @Test
    void notFound ()
    {
        assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().retrieve(HttpRequest.GET("/fail"))
        );
    }
}
