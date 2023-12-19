package org.example.adapter.out.database.repository.mappers;

import org.example.domain.money.Money;

import java.math.BigDecimal;

public interface MoneyMapper {
    Money mapToDomainMoneyFromDatabase(String currency, BigDecimal amount);
}
