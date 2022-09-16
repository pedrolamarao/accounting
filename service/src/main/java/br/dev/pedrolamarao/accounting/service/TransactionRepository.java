package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
abstract class TransactionRepository implements CrudRepository<AccountingTransaction, Integer>
{
}
