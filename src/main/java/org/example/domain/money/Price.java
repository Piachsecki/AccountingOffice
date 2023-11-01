package org.example.domain.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Price(BigDecimal amount, Currency currency) {
    public Price {
        Objects.requireNonNull(currency, "'currency' must not be null");
        Objects.requireNonNull(amount, "'amount' must not be null");
    }

    public BigDecimal countNETTOvalue(Vat vatTypeRate){
        switch (vatTypeRate){
            case VAT0 -> {
                return amount;
            }
            case VAT5 -> {
                return amount.subtract(BigDecimal.valueOf(0.05).multiply(amount).setScale(2, RoundingMode.UP));
            }
            case VAT8 -> {
                return  amount.subtract(BigDecimal.valueOf(0.08).multiply(amount).setScale(2, RoundingMode.UP));
            }
            case VAT23 -> {
                return  amount.subtract(BigDecimal.valueOf(0.23).multiply(amount).setScale(2, RoundingMode.UP));
            }
            default -> throw new RuntimeException("There is not vatTypeRate like this!");
        }
    }
}
