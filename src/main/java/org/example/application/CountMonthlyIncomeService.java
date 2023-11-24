package org.example.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.application.exceptions.CountMonthlyIncomeException;
import org.example.domain.customer.Customer;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountMonthlyCostsUseCase;
import org.example.port.in.invoice.CountMonthlyIncomeUseCase;

import java.math.BigDecimal;
import java.time.YearMonth;

@AllArgsConstructor
@Slf4j

public class CountMonthlyIncomeService implements CountMonthlyIncomeUseCase {
    private CountMonthlyMonthlyRevenueService countMonthlyRevenueService;
    private CountMonthlyCostsUseCase countMonthlyCostsUseCase;
    private CountIncomeTaxService countIncomeTaxService;
    @Override
    public Money countMonthlyIncome(Customer customer, YearMonth yearMonth) {
        if (yearMonth.isAfter(YearMonth.now())){
            log.error("We cannot calculate the revenue for the specified month [{}]," +
                    " because now we have [{}] and it is impossible to calculate" +
                    "revenue for the upcoming months!", yearMonth, YearMonth.now());
            throw new CountMonthlyIncomeException("We cannot calculate the revenue for the upcoming months!");
        }

        Money monthlyRevenue = countMonthlyRevenueService.countMonthlyRevenue(customer.getCustomerId(), yearMonth);
        Money monthlyCosts = countMonthlyCostsUseCase.countMonthlyCosts(customer, yearMonth);
        Money tax = countIncomeTaxService.countIncomeTax(customer, yearMonth);
        BigDecimal finalIncome = monthlyRevenue.amount().subtract(monthlyCosts.amount()).subtract(tax.amount());
        return new Money(finalIncome, Currency.PLN);

    }
}
