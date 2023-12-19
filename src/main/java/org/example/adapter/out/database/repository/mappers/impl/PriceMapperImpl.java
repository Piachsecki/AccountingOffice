package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.repository.mappers.PriceMapper;
import org.example.domain.money.Price;

import java.math.BigDecimal;

public class PriceMapperImpl implements PriceMapper {
    @Override
    public Price mapToDomainPriceFromDatabase(String currency, BigDecimal amount) {
        return null;
    }
}
