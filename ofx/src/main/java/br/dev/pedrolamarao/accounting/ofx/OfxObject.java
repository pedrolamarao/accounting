package br.dev.pedrolamarao.accounting.ofx;

public sealed interface OfxObject permits OfxField
{
    OfxObjectType type ();
}
