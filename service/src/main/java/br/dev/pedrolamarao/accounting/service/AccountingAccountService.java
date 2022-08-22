package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import br.dev.pedrolamarao.accounting.model.AccountingTransaction;

import java.util.List;

public interface AccountingAccountService
{
    // accounts

    long createAccount (AccountingAccount account);

    void deleteAccount (long id);

    List<Listed<AccountingAccount>> listAccount (int page);

    AccountingAccount retrieveAccount (long id);

    void updateAccount (long id, AccountingAccount account);

    // transactions

    long createTransaction (long account, AccountingTransaction transaction);

    AccountingTransaction deleteTransaction (long account, long transaction);

    List<Listed<AccountingTransaction>> listTransactions (long account, int page);

    AccountingTransaction retrieveTransaction (long account, long transaction);

    AccountingTransaction updateTransaction (long account, long transaction, AccountingTransaction value);
}
