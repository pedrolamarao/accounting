package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingTransaction;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

class OfxTransactionReader
{
    public List<AccountingTransaction> read (InputStream source) throws IOException
    {
        return Collections.emptyList();
    }
}
