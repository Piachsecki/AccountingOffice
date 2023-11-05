package org.example.domain.money;

import java.math.BigDecimal;
import java.util.Objects;

public record Income(BigDecimal amount, Currency currency) {
    public Income {
        Objects.requireNonNull(currency, "'currency' must not be null");
        Objects.requireNonNull(amount, "'amount' must not be null");
    }
}
