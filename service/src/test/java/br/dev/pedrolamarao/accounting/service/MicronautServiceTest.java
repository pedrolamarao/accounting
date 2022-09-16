package br.dev.pedrolamarao.accounting.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class MicronautServiceTest extends ServiceTest
{
    @Inject
    AccountingMicronautService service;

    @Override public AccountingService service ()
    {
        return service;
    }
}
