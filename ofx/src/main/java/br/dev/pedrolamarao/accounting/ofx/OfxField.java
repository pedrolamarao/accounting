package br.dev.pedrolamarao.accounting.ofx;

public record OfxField(String name, String value) implements OfxObject
{
    public OfxObjectType type ()
    {
        return OfxObjectType.Field;
    }
}
