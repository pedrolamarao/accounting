package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;

import java.util.List;

public interface AccountingService
{
    // accounts

    long createAccount (AccountingAccount account);

    AccountingAccount deleteAccount (long id);

    List<AccountingAccount> listAccount (int page);

    AccountingAccount retrieveAccount (long id);

    AccountingAccount updateAccount (long id, AccountingAccount account);

    // transactions

    long createTransaction (AccountingTransaction transaction);

    AccountingTransaction deleteTransaction (long transaction);

    List<AccountingTransaction> listTransactions (long account, int page);

    AccountingTransaction retrieveTransaction (long transaction);

    AccountingTransaction updateTransaction (AccountingTransaction value);
}
