package br.dev.pedrolamarao.accounting.ofx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class OfxParser
{
    public static List<OfxTransaction> parse (String ofx)
    {
        final var transactions = new ArrayList<OfxTransaction>();

        final var lines = ofx.lines().iterator();
        String line;

        // skip until BANKTRANLIST
        while (lines.hasNext() && !lines.next().startsWith("<BANKTRANLIST>"))
            continue;
        if (!lines.hasNext())  throw new RuntimeException("unexpected OFX");
        line = lines.next();
        if (!line.startsWith("<DTSTART>")) throw new RuntimeException("unexpected OFX");
        final var start = line;
        if (!lines.hasNext())  throw new RuntimeException("unexpected OFX");
        line = lines.next();
        if (!line.startsWith("<DTEND>")) throw new RuntimeException("unexpected OFX");
        final var end = line;

        while (lines.hasNext()) {
            if (!lines.next().startsWith("<STMTTRN>")) break;
            transactions.add( parseTransaction(lines) );
        }

        return transactions;
    }

    final Pattern linePattern = Pattern.compile("<[^>]+>([^<]+)</[^>]+>");

    static OfxTransaction parseTransaction (Iterator<String> lines)
    {
        String amount = null;
        String date = null;
        String description = null;
        String id = null;
        String type = null;
        while (lines.hasNext()) {
            final var line = lines.next();
            if (line.startsWith("</STMTTRN>")) break;
            else if (line.startsWith("<TRNTYPE>")) type = line;
            else if (line.startsWith("<DTPOSTED>")) date = line;
            else if (line.startsWith("<TRNAMT>")) amount = line;
            else if (line.startsWith("<FITID>")) id = line;
            else if (line.startsWith("<MEMO>")) description = line;
        }
        return new OfxTransaction(amount,date,description,id,type);
    }
}
