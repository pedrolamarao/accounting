// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonetaryQuantityTest
{
    @Test
    public void parse()
    {
        assertEquals(
            new MonetaryQuantity(2440),
            MonetaryQuantity.parse("24,40",'.',',')
        );
        assertEquals(
            new MonetaryQuantity(-2440),
            MonetaryQuantity.parse("-24,40",'.',',')
        );
        assertEquals(
            new MonetaryQuantity(112000),
            MonetaryQuantity.parse("1.120,00",'.',',')
        );
        assertEquals(
            new MonetaryQuantity(-112000),
            MonetaryQuantity.parse("-1.120,00",'.',',')
        );
        assertEquals(
            new MonetaryQuantity(69500),
            MonetaryQuantity.parse("695.00",',','.')
        );
        assertEquals(
            new MonetaryQuantity(-69500),
            MonetaryQuantity.parse("-695.00",',','.')
        );
        assertEquals(
            new MonetaryQuantity(269627),
            MonetaryQuantity.parse("2696.27",',','.')
        );
        assertEquals(
            new MonetaryQuantity(-269627),
            MonetaryQuantity.parse("-2696.27",',','.')
        );
    }
}
