package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class OfxTransactionReaderTest
{
    @Test
    void statement_20220920 () throws IOException
    {
        final List<AccountingTransaction> expected;

        try (var stream = getClass().getClassLoader().getResourceAsStream("statement-20220920.json"))
        {
            expected = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .readerForListOf(AccountingTransaction.class)
                .readValue(stream);
        }

        assertEquals( 10, expected.size() );

        final List<AccountingTransaction> actual;

        try (var stream = getClass().getClassLoader().getResourceAsStream("statement-20220920.ofx"))
        {
            actual = new OfxTransactionReader().read(stream);
        }

        assertIterableEquals( expected, actual );
    }
}
