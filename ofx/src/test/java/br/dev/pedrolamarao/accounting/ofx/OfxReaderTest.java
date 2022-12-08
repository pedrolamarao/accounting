package br.dev.pedrolamarao.accounting.ofx;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OfxReaderTest
{
    @Test
    public void empty () throws Exception
    {
        final var reader = OfxReader.from( new ByteArrayInputStream( new byte[0] ) );
        assertNull( reader.next() );
    }

    @Test
    public void inter_20220721 () throws Exception
    {
        try (var stream = getClass().getClassLoader().getResourceAsStream("inter-20220721.ofx"))
        {
            final var reader = OfxReader.from(stream);

            assertThat( reader.next(), equalTo( new OfxField("OFXHEADER","100") ) );
            assertThat( reader.next(), equalTo( new OfxField("DATA","OFXSGML") ) );
            assertThat( reader.next(), equalTo( new OfxField("VERSION","102") ) );
            assertThat( reader.next(), equalTo( new OfxField("SECURITY","NONE") ) );
            assertThat( reader.next(), equalTo( new OfxField("ENCODING","USASCII") ) );
            assertThat( reader.next(), equalTo( new OfxField("CHARSET","1252") ) );
            assertThat( reader.next(), equalTo( new OfxField("COMPRESSION","NONE") ) );
            assertThat( reader.next(), equalTo( new OfxField("OLDFILEUID","NONE") ) );
            assertThat( reader.next(), equalTo( new OfxField("NEWFILEUID","NONE") ) );
            assertThat( reader.next(), equalTo(null) );
        }
    }

    @Test
    public void nubank_20220721 () throws Exception
    {
        try (var stream = getClass().getClassLoader().getResourceAsStream("nubank-20220721.ofx"))
        {
            final var reader = OfxReader.from(stream);

            assertThat( reader.next(), equalTo( new OfxField("OFXHEADER","100") ) );
            assertThat( reader.next(), equalTo( new OfxField("DATA","OFXSGML") ) );
            assertThat( reader.next(), equalTo( new OfxField("VERSION","102") ) );
            assertThat( reader.next(), equalTo( new OfxField("SECURITY","NONE") ) );
            assertThat( reader.next(), equalTo( new OfxField("ENCODING","UTF-8") ) );
            assertThat( reader.next(), equalTo( new OfxField("CHARSET","NONE") ) );
            assertThat( reader.next(), equalTo( new OfxField("COMPRESSION","NONE") ) );
            assertThat( reader.next(), equalTo( new OfxField("OLDFILEUID","NONE") ) );
            assertThat( reader.next(), equalTo( new OfxField("NEWFILEUID","NONE") ) );
            assertThat( reader.next(), equalTo(null) );
        }
    }
}
