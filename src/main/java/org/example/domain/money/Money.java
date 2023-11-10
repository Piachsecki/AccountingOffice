package org.example.domain.money;

import java.math.BigDecimal;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(currency, "'currency' must not be null");
        Objects.requireNonNull(amount, "'amount' must not be null");
    }

    public BigDecimal countToPLN(){
        if (!this.currency.equals(Currency.PLN)){
            BigDecimal exchange_rate = new BigDecimal(currency.getExchange_rate());
            return exchange_rate.multiply(amount);
        }
        return amount;
    }
}
