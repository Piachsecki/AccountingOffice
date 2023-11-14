package org.example.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.customer.Customer;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountMonthlyCostsUseCase;
import org.example.port.out.InvoiceRepository;

import java.math.BigDecimal;
import java.time.YearMonth;

@Slf4j
@AllArgsConstructor
public class CountMonthlyCostsService implements CountMonthlyCostsUseCase {
    private InvoiceRepository invoiceRepository;
    @Override
    public Money countMonthlyCosts(Customer customer, YearMonth yearMonth) {
        BigDecimal amount = BigDecimal.ZERO;
        if (yearMonth.isAfter(YearMonth.now())){
            log.error("We cannot calculate the revenue for the specified month [{}]," +
                    " because now we have [{}] and it is impossible to calculate" +
                    "revenue for the upcoming months!", yearMonth, YearMonth.now());
            throw new RuntimeException("We cannot calculate the revenue for the upcoming months!");
        }
        amount = amount.add(invoiceRepository.countMonthlyCosts(customer.getCustomerId(), yearMonth).amount());
        return new Money(amount, Currency.PLN);
    }
}
