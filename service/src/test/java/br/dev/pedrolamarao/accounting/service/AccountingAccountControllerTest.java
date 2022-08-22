package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingAccountType;
import io.micronaut.core.type.Argument;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.micronaut.http.HttpRequest.*;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class AccountingAccountControllerTest
{
    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    AccountingAccountServiceMemory service;

    @BeforeEach
    public void rest () { service.reset(); }

    @DisplayName("create,delete account")
    @Test
    void createDeleteAccount ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        final var create = client.toBlocking().exchange(
            POST("/accounts",account),
            Argument.of(Stored.class,AccountingAccount.class)
        );
        assertEquals(201,create.code());
        assertNotNull(create.body().uri());
        assertEquals(account,create.body().value());

        final var delete = client.toBlocking().exchange(
            DELETE(create.body().uri(),Void.class)
        );
        assertEquals(200,delete.code());
    }

    @DisplayName("create,list account")
    @Test
    void createListAccount ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        final var create = client.toBlocking().exchange(
            POST("/accounts",account),
            Argument.of(Stored.class,AccountingAccount.class)
        );
        assertEquals(201,create.code());

        final var list = client.toBlocking().exchange(
            GET("/accounts"),
            Argument.of(Paged.class,Argument.of(Stored.class,AccountingAccount.class))
        );
        assertEquals(200,list.code());
        assertIterableEquals(List.of(create.body()),list.body().values());
    }

    @DisplayName("create,retrieve account")
    @Test
    void createRetrieveAccount ()
    {
        final var account = new AccountingAccount(AccountingAccountType.ASSET,"name");

        final var create = client.toBlocking().exchange(
            POST("/accounts",account),
            Argument.of(Stored.class,AccountingAccount.class)
        );
        assertEquals(201,create.code());

        final var retrieve = client.toBlocking().exchange(
            GET(create.body().uri()),
            Argument.of(Stored.class,AccountingAccount.class)
        );
        assertEquals(200,retrieve.code());
        assertEquals(create.body(),retrieve.body());
    }

    @DisplayName("create,update account")
    @Test
    void createUpdateAccount ()
    {
        final var first = new AccountingAccount(AccountingAccountType.ASSET,"name");
        final var second = new AccountingAccount(AccountingAccountType.ASSET,"NAME");

        final var create = client.toBlocking().exchange(
            POST("/accounts",first),
            Argument.of(Stored.class,AccountingAccount.class)
        );
        assertEquals(201,create.code());

        final var update = client.toBlocking().exchange(
            PUT(create.body().uri(),second),
            Argument.of(Stored.class,AccountingAccount.class)
        );
        assertEquals(200,update.code());
        assertEquals(create.body().uri(),update.body().uri());
        assertEquals(second,update.body().value());
    }

    @DisplayName("list accounts")
    @Test
    void listAccounts ()
    {
        final var list = client.toBlocking().exchange(
            GET("/accounts"),
            Argument.of(Paged.class,Argument.of(Stored.class,AccountingAccount.class))
        );
        assertEquals(200,list.code());

        final var again = client.toBlocking().exchange(
            GET(list.body().current()),
            Argument.of(Paged.class,Argument.of(Stored.class,AccountingAccount.class))
        );
        assertEquals(200,again.code());
        assertEquals(list.body(),again.body());
    }
}
