package br.dev.pedrolamarao.accounting.service;

public class MemoryServiceTest extends ServiceTest
{
    @Override
    public AccountingAccountService service ()
    {
        return new AccountingAccountServiceMemory();
    }
}
