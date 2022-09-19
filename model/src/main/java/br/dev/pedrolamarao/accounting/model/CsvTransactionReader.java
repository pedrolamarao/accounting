// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.model;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;

public class CsvTransactionReader implements AccountingTransactionReader
{
    private final CsvModel model;

    private final Reader reader;

    private transient int line = 0;

    public CsvTransactionReader (CsvModel model, Reader reader)
    {
        this.model = model;
        this.reader = reader;
    }

    /**
     * Read next transaction.
     *
     * @return null if and only if this reader is finished
     */

    public AccountingTransaction read () throws IOException
    {
        final var list = new ArrayList<String>();

        while (line < model.firstLine()) {
            final var code = reader.read();
            if (code == '\n') { line += 1; }
        }

        outer: while (true) {
            final var builder = new StringBuilder();
            while (true) {
                final var code = reader.read();
                if (code == -1) return null;
                else if (code == model.recordSeparator()) break;
                else if (code == '\n') { line += 1; break outer; }
                else if (code != '\r') builder.append( Character.toChars(code) );
            }
            list.add( builder.toString() );
        }

        final var moneys = MonetaryQuantity.parse( list.get( model.amountColumn() ), model.thousandSeparator(), model.centSeparator() );
        final var type = (moneys.quantity() < 0) ? AccountingTransactionType.DEBIT : AccountingTransactionType.CREDIT;

        return new AccountingTransaction(-1,-1,type,LocalDate.now(),moneys.quantity(),"");
    }
}