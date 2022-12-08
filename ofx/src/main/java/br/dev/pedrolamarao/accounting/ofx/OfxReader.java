package br.dev.pedrolamarao.accounting.ofx;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class OfxReader
{
    private final InputStream source;

    enum Stage { Fields, Markup }

    private Stage stage = Stage.Fields;

    private OfxReader (InputStream source)
    {
        this.source = source;
    }

    public static OfxReader from (InputStream source)
    {
        return new OfxReader(source);
    }

    public OfxObject next () throws Exception
    {
        if (stage == Stage.Fields)
        {
            final var field = nextField();
            if (field == null) {
                stage = Stage.Markup;
            }
            // Nubank OFX defect: no separator empty line.
            else if ("NEWFILEUID".contentEquals(field.name())) {
                stage = Stage.Markup;
                return field;
            }
            else {
                return field;
            }
        }

        return nextMarkup();
    }

    OfxField nextField () throws Exception
    {
        final var line = nextLine();

        if (line.length == 0)
            return null;

        final int marker = find(line,0,line.length,':');

        if (marker == -1)
            throw new RuntimeException("field separator not found");

        return new OfxField(
            new String( line, 0, marker, US_ASCII ),
            new String( line, marker + 1, line.length - (marker + 1), US_ASCII )
        );
    }

    OfxObject nextMarkup () throws Exception
    {
        return null;
    }

    byte[] nextLine () throws Exception
    {
        final var buffer = new ByteArrayOutputStream();
        int next;
        while ((next = source.read()) != -1) {
            if (next == '\r') continue;
            if (next == '\n') break;
            buffer.write(next);
        }
        return buffer.toByteArray();
    }

    static int find (byte[] bytes, int offset, int length, int target)
    {
        for (int i = offset; i != length; ++i)
            if (bytes[i] == target)
                return i;
        return -1;
    }
}