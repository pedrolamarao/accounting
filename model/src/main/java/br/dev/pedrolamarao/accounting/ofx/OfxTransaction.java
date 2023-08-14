package br.dev.pedrolamarao.accounting.ofx;

public record OfxTransaction (String amount, String date, String description, String id, String type)
{
}
