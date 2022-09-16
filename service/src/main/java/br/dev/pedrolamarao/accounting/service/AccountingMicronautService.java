package br.dev.pedrolamarao.accounting.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
class AccountingMicronautService
{
    @Inject AccountRepository accounts;

    @Inject TransactionRepository transactions;
}
