package br.dev.pedrolamarao.accounting.service;

import br.dev.pedrolamarao.accounting.model.AccountingTransaction;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@JdbcRepository(dialect = Dialect.H2)
abstract class TransactionRepository implements CrudRepository<AccountingTransaction,Long>
{
    @Join(value = "account", type = Join.Type.FETCH)
    @Override
    @NonNull
    public abstract Optional<AccountingTransaction> findById (@NonNull Long aLong);

    @Join(value = "account", type = Join.Type.FETCH)
    @Override
    @NonNull
    public abstract Iterable<AccountingTransaction> findAll ();
}
