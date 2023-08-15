module br.dev.pedrolamarao.accounting.tool
{
    exports br.dev.pedrolamarao.accounting.tool;
    requires info.picocli;
    requires jakarta.inject;
    requires java.net.http;
    requires io.micronaut.http_client_core;
    requires io.micronaut.picocli.picocli;
    requires io.micronaut.http;
    opens br.dev.pedrolamarao.accounting.tool to info.picocli;
}
