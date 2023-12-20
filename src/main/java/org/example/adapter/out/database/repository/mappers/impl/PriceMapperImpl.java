package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.repository.mappers.PriceMapper;
import org.example.domain.money.Currency;
import org.example.domain.money.Price;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service

public class PriceMapperImpl implements PriceMapper {
    @Override
    public Price mapToDomainPriceFromDatabase(String currency, BigDecimal amount) {
        return new Price(
                amount,
                Currency.valueOf(currency));
    }
}
