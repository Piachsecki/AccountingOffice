package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.repository.mappers.MoneyMapper;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service

public class MoneyMapperImpl implements MoneyMapper {
    @Override
    public Money mapToDomainMoneyFromDatabase(String currency, BigDecimal amount) {
        return new Money(amount,
                Currency.valueOf(currency)
        );

    }
}
