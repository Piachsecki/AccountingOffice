package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.repository.mappers.MoneyMapper;
import org.example.domain.money.Money;

import java.math.BigDecimal;

public class MoneyMapperImpl implements MoneyMapper {
    @Override
    public Money mapToDomainMoneyFromDatabase(String currency, BigDecimal amount) {
        return null;
    }
}
