package org.example.application;

import org.example.domain.customer.Customer;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountIncomeTaxUseCase;
import org.example.port.in.invoice.CountMonthlyCostsUseCase;

import java.time.YearMonth;

public class CountIncomeTaxService implements CountIncomeTaxUseCase {
    private CountMonthlyRevenueService countMonthlyRevenueService;
    private CountMonthlyCostsService countMonthlyCostsService;

    @Override
    public Money countIncomeTax(Customer customer, YearMonth yearMonth) {
        return null;
    }
}
