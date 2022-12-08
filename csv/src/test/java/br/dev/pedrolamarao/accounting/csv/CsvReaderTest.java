package br.dev.pedrolamarao.accounting.csv;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CsvReaderTest
{
    @Test
    public void empty () throws Exception
    {
        final var reader = CsvReader.from( new ByteArrayInputStream( new byte[0] ), UTF_8, ',');
        assertNull( reader.next() );
    }

    @Test
    public void inter_20220721 () throws Exception
    {
        try (var stream = getClass().getClassLoader().getResourceAsStream("inter-20220721.csv"))
        {
            final var reader = CsvReader.from( stream, UTF_8, ';', 5 );
            assertThat(
                reader.next(),
                arrayContaining(
                    "DATA LANÇAMENTO",
                    "HISTÓRICO",
                    "VALOR",
                    "SALDO"
                )
            );
            assertThat(
                reader.next(),
                arrayContaining(
                    "02/05/2022",
                    "COMPRA CARTAO - No estabelecimento PANIFICADORA BARRA PAO SAO PAUL",
                    "-24,40",
                    "16.068,16"
                )
            );
        }
    }

    @Test
    public void nubank_20220721 () throws Exception
    {
        try (var stream = getClass().getClassLoader().getResourceAsStream("nubank-20220721.csv"))
        {
            final var reader = CsvReader.from( stream, UTF_8, ',' );
            assertThat(
                reader.next(),
                arrayContaining(
                    "Data",
                    "Valor",
                    "Identificador",
                    "Descrição"
                )
            );
            assertThat(
                reader.next(),
                arrayContaining(
                    "03/06/2022",
                    "695.00",
                    "629a0a3f-8cae-4b70-958d-ed8cb8a54349",
                    "Transferência recebida pelo Pix - PEDRO LAMAR?O ROSA E SILVA - 095.374.767-07 - BANCO ORIGINAL (0212) Agência: 1 Conta: 138156-3"
                )
            );
            assertThat(
                reader.next(),
                arrayContaining(
                    "03/06/2022",
                    "-2696.27",
                    "629a0a53-4c6c-4a4a-9934-0724edd111b5",
                    "Pagamento de fatura"
                )
            );
            assertNull( reader.next() );
        }
    }
}
