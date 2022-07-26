package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;

import java.util.List;

public interface AccountingAccountService
{
    long create (AccountingAccount account);

    void delete (long id);

    List<Listed<AccountingAccount>> list (int page);

    AccountingAccount retrieve (long id);

    void update (long id, AccountingAccount account);
}
