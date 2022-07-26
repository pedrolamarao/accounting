// Copyright (C) 2022 Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.accounting.model;

import java.util.Objects;

/**
 * Monetary quantity.
 *
 * @param quantity  monetary quantity
 */
public record MonetaryQuantity (long quantity)
{
    public static MonetaryQuantity parse (String moneys, char thousands, char cents)
    {
        Objects.requireNonNull(moneys);

        long quantity = 0;
        int index = 0;

        final long sign;

        {
            if (index >= moneys.length()) throw new NumberFormatException("expected sign or digit at index " + index + ", found end-of-string");
            final char code = moneys.charAt(index);
            sign = code == '-' ? -1 : 1;
            index = Character.isDigit(code) ? 0 : 1;
        }

        {
            if (index >= moneys.length()) throw new NumberFormatException("expected digit at index " + index + ", found end-of-string");
            final char code = moneys.charAt(index);
            if (Character.isDigit(code)) { quantity = Character.digit(code,10); ++index; }
            else throw new NumberFormatException("expected digit at index " + index + ", found " + code);
        }

        while (index < moneys.length())
        {
            final char code = moneys.charAt(index);
            if (Character.isDigit(code)) { quantity = quantity * 10 + Character.digit(code,10); ++index; }
            else if (code == thousands) ++index;
            else if (code == cents) break;
            else throw new NumberFormatException("expected digit or separator at index " + index + ", found " + code);
        }

        if (moneys.charAt(index) == cents)
        {
            ++index;

            for (int j = 0; j != 2; ++j)
            {
                if (index >= moneys.length()) throw new NumberFormatException("expected digit at index " + index + ", found end-of-string");
                final char code = moneys.charAt(index);
                if (Character.isDigit(code)) { quantity = quantity * 10 + Character.digit(code,10); ++index; }
                else throw new NumberFormatException("expected digit at index " + index + ", found " + code);
            }
        }

        if (index != moneys.length()) throw new NumberFormatException("expected end-of-string at index " + index);

        return new MonetaryQuantity(sign * quantity);
    }
}
