// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.model;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import javax.validation.constraints.NotBlank;

@MappedEntity
public record AccountingAccount (
    @GeneratedValue @Id long id,
    @NonNull AccountingAccountType type,
    @NonNull @NotBlank String name
)
{
    public AccountingAccount withId (long id)
    {
        return new AccountingAccount(id,type(),name());
    }
}
