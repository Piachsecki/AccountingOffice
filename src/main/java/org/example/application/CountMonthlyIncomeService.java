package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountHealthInsuranceContributionUseCase;
import org.example.port.in.invoice.CountMonthlyCostsUseCase;
import org.example.port.in.invoice.CountMonthlyIncomeUseCase;
import org.example.port.out.InvoiceRepository;

import java.time.YearMonth;

@AllArgsConstructor
public class CountMonthlyIncomeService implements CountMonthlyIncomeUseCase {
    private CountMonthlyRevenueService countMonthlyRevenueService;
    private CountMonthlyCostsUseCase countCostsRevenueService;
    private CountIncomeTaxService countIncomeTaxService;
    @Override
    public Money countMonthlyIncome(Customer customer, YearMonth monthToCalculateTheIncome) {
        return null;
    }
}
