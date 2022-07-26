package br.dev.pedrolamarao.accounting.tool;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.net.URI;

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

        @CommandLine.Command(name="list")
        public int list ()
        {
            System.out.println( http.toBlocking().retrieve( service.resolve("/accounts").toString() ) );
            return 0;
        }
    }

    public static void main (String... args)
    {
        System.exit( PicocliRunner.execute(AccountingTool.class,args) );
    }
}
