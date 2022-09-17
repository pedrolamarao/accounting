// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.model;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

import static io.micronaut.data.annotation.Relation.Kind.MANY_TO_ONE;

@MappedEntity
public record AccountingTransaction (
    @GeneratedValue @Id long id,
    @NonNull @Relation(value=MANY_TO_ONE) AccountingAccount account,
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

    public AccountingTransaction withAccount (AccountingAccount account)
    {
        return new AccountingTransaction(id(),account,type(),date(),moneys(),description());
    }
}
