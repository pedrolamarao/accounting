package br.dev.pedrolamarao.accounting.model;

public record AccountingAccount(
    AccountingAccountType type,
    String name
)
{
}
