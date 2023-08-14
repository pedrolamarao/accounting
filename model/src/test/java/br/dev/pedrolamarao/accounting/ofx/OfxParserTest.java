package br.dev.pedrolamarao.accounting.ofx;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OfxParserTest {
    record Item (String resource, int size) { }

    @ParameterizedTest
    @MethodSource("items")
    void test (Item item) throws Exception
    {
        final String ofx;
        try (var stream = getClass().getResourceAsStream(item.resource())) {
            ofx = new String( stream.readAllBytes(), US_ASCII );
        }

        final var transactions = OfxParser.parse(ofx);

        assertNotNull(transactions);
        assertEquals(item.size(),transactions.size());
    }

    static List<Item> items ()
    {
        return List.of(
            new Item("/inter-20220721.ofx",77),
            new Item("/nubank-20220721.ofx",2)
        );
    }
}
