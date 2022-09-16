package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingAccount;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.H2)
abstract class AccountRepository implements CrudRepository<AccountingAccount,Long>
{
}
