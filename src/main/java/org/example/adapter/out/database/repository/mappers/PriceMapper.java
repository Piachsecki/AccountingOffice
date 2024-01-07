package org.example.adapter.out.database.repository.mappers;

import org.example.domain.money.Price;

import java.math.BigDecimal;

public interface PriceMapper {
    Price mapToDomainPriceFromDatabase(String currency, BigDecimal amount);
}
