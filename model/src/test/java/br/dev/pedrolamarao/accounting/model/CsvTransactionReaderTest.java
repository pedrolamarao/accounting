// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.model;

import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CsvTransactionReaderTest
{
    @Test
    public void parseInter () throws Exception
    {
        final var inter = new CsvModel(';',6,0,2,1,'.',',');
        try (var stream = getClass().getResourceAsStream("/inter-20220721.csv"))
        {
            assertNotNull(stream);
            final var reader = new CsvTransactionReader(inter,new InputStreamReader(stream));
            final var transaction = reader.read();
            assertNotNull(transaction);
            assertEquals(AccountingTransactionType.DEBIT,transaction.type());
            assertEquals(-2440,transaction.moneys());
        }
    }

    @Test
    public void parseNubank () throws Exception
    {
        final var inter = new CsvModel(',',1,0,1,3,',','.');
        try (var stream = getClass().getResourceAsStream("/nubank-20220721.csv"))
        {
            assertNotNull(stream);
            final var reader = new CsvTransactionReader(inter,new InputStreamReader(stream));
            final var transaction = reader.read();
            assertNotNull(transaction);
            assertEquals(AccountingTransactionType.CREDIT,transaction.type());
            assertEquals(69500,transaction.moneys());
        }
    }
}
