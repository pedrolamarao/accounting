package br.dev.pedrolamarao.accounting.tool;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

@CommandLine.Command(
    name="accounting-tool",
    subcommands={
        AccountingTool.Accounts.class
    }
)
public class AccountingTool
{
    @CommandLine.Command(name="accounts")
    public static class Accounts
    {
        @Client
        @Inject
        HttpClient http;

        @CommandLine.Option(names = "--service", required = true, scope = CommandLine.ScopeType.INHERIT)
        URI service;

        @CommandLine.Command(name="create")
        public int create (
            @CommandLine.Option(names="--file",required=true) Path path
        )
            throws Exception
        {
            final var json = http.toBlocking().retrieve(
                    HttpRequest.POST(
                        service.resolve("/accounts"),
                        Files.readAllBytes(path)
                    )
                );
            System.err.println(json);
            return 0;
        }

        @CommandLine.Command(name="delete")
        public int delete (
            @CommandLine.Option(names="--id",required=true) long id
        )
        {
            final var response = http.toBlocking().exchange(
                HttpRequest.DELETE(
                    service.resolve("/accounts/" + id),
                    null
                )
            );
            System.err.println(response);
            return 0;
        }

        @CommandLine.Command(name="list")
        public int list ()
        {
            final var json = http.toBlocking().retrieve(
                HttpRequest.GET(
                    service.resolve("/accounts")
                )
            );
            System.err.println(json);
            return 0;
        }

        @CommandLine.Command(name="retrieve")
        public int retrieve (
            @CommandLine.Option(names="--id",required=true) long id
        )
        {
            final var json = http.toBlocking().retrieve(
                HttpRequest.GET(
                    service.resolve("/accounts/" + id)
                )
            );
            System.err.println(json);
            return 0;
        }

        @CommandLine.Command(name="update")
        public int update (
            @CommandLine.Option(names="--file",required=true) Path path,
            @CommandLine.Option(names="--id",required=true) long id
        )
            throws Exception
        {
            final var json = http.toBlocking().retrieve(
                HttpRequest.PUT(
                    service.resolve("/accounts/" + id),
                    Files.readAllBytes(path)
                )
            );
            System.err.println(json);
            return 0;
        }
    }

    public static void main (String... args)
    {
        System.exit( PicocliRunner.execute(AccountingTool.class,args) );
    }
}
