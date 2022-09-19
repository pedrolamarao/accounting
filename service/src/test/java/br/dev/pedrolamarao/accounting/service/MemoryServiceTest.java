package br.dev.pedrolamarao.accounting.service;

public class MemoryServiceTest extends ServiceTest
{
    @Override
    public AccountingService service ()
    {
        return new AccountingMemoryService();
    }
}
