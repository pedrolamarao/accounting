// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.model;

import java.time.LocalDate;

public record AccountingTransaction (
    AccountingTransactionType type,
    LocalDate date,
    MonetaryQuantity moneys,
    String description
)
{ }
