package org.example.adapter.out.database.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.example.domain.money.Currency;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public record MoneyDatabase(BigDecimal amount,@Enumerated(EnumType.STRING) Currency currency) {
    public MoneyDatabase {
        Objects.requireNonNull(currency, "'currency' must not be null");
        Objects.requireNonNull(amount, "'amount' must not be null");
    }

}
