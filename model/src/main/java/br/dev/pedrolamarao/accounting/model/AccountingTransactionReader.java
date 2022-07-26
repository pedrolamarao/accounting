// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.model;

import java.io.IOException;

public interface AccountingTransactionReader
{
    /**
     * Read next transaction.
     *
     * @return null if and only if reader is finished
     */
    AccountingTransaction read () throws IOException;
}
