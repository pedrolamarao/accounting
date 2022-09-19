// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.model;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@MappedEntity
public record AccountingTransaction (
    @GeneratedValue @Id long id,
    @NonNull long account,
    @NonNull AccountingTransactionType type,
    @NonNull LocalDate date,
    long moneys,
    @NonNull @NotBlank String description
)
{
    public AccountingTransaction withId (long id)
    {
        return new AccountingTransaction(id,account(),type(),date(),moneys(),description());
    }
}
