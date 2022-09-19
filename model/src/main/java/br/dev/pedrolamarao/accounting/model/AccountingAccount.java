package br.dev.pedrolamarao.accounting.model;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

@MappedEntity
public record AccountingAccount (
    @GeneratedValue @Id long id,
    @NonNull AccountingAccountType type,
    @NonNull String name
)
{
    public AccountingAccount withId (long id)
    {
        return new AccountingAccount(id,type(),name());
    }
}

