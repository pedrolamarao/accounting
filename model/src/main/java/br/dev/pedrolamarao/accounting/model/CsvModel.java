// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.model;

/**
 * CSV transaction file mode.
 *
 * @param recordSeparator    record separator
 * @param firstLine          first transaction line number
 * @param dateColumn         transaction date column index
 * @param amountColumn       transaction amount column index
 * @param descriptionColumn  transaction description colum index
 * @param thousandSeparator  monetary thousand separator
 * @param centSeparator      monetary cent separator
 */
public record CsvModel
(
    char recordSeparator,
    int firstLine,
    int dateColumn,
    int amountColumn,
    int descriptionColumn,
    char thousandSeparator,
    char centSeparator
)
{
}
