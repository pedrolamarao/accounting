package br.dev.pedrolamarao.accounting.csv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class CsvReader
{
    private final char separator;

    private int skip;

    private final BufferedReader source;

    private CsvReader (char separator, int skip, Reader source)
    {
        this.separator = separator;
        this.skip = skip;
        this.source = new BufferedReader(source);
    }

    public static CsvReader from (InputStream source, Charset charset, char separator, int skip)
    {
        return new CsvReader( separator, skip, new InputStreamReader(source,charset) );
    }

    public static CsvReader from (InputStream source, Charset charset, char separator)
    {
        return new CsvReader( separator, 0, new InputStreamReader(source,charset) );
    }

    public String[] next () throws Exception
    {
        if (skip > 0) while (skip-- > 0) source.readLine();
        final var line = source.readLine();
        if (line == null) return null;
        return line.split("" + separator);
    }
}
