package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class AccountingAccountControllerTest
{
    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    record PagedStored <T> (URI current, URI next, URI previous, List<Stored<T>> values) { }

    @Test
    void createAccount()
    {
        final var account = new AccountingAccount("description");
        final var post = client.toBlocking().retrieve(HttpRequest.POST("/accounts",account),Stored.class);
        final var list = client.toBlocking().retrieve(HttpRequest.GET("/accounts"),PagedStored.class);
        assertNotNull(list.current());
        assertNull(list.next());
        assertNull(list.previous());
        assertEquals(1,list.values().size());
        assertEquals(post,list.values().get(0));
    }

    @Test
    void listAccounts ()
    {
        final var list = client.toBlocking().retrieve(HttpRequest.GET("/accounts"),Paged.class);
        assertNotNull(list.current());
        assertNull(list.next());
        assertNull(list.previous());
        final var current = client.toBlocking().retrieve(list.current().toString(),Paged.class);
        assertEquals(list.current(),current.current());
    }

    @Test
    void updateAccount ()
    {
        final var first = new AccountingAccount("description");
        final var second = new AccountingAccount("DESCRIPTION");
        final var create = client.toBlocking().retrieve(HttpRequest.POST("/accounts",first),Stored.class);
        final var update = client.toBlocking().retrieve(HttpRequest.PUT(create.uri(),second),Stored.class);
        assertEquals(create.uri(),update.uri());
        assertNotEquals(create.value(),update.value());
    }
}
