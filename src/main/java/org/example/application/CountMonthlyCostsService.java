package org.example.application;

import org.example.domain.customer.Customer;
import org.example.domain.customer.TaxPayments.TaxRate;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountMonthlyCostsUseCase;
import org.example.port.out.InvoiceRepository;

import java.math.BigDecimal;
import java.time.YearMonth;

public class CountMonthlyCostsService implements CountMonthlyCostsUseCase {
    private InvoiceRepository invoiceRepository;
    private CountHealthInsuranceContributionService countHealthInsuranceContributionService;

    @Override
    public Money countMonthlyCosts(Customer customer, YearMonth yearMonth) {
        Money costs = new Money(BigDecimal.ZERO, Currency.PLN);
//        costs.amount() = costs.amount().add(invoiceRepository.countMonthlyCosts().amount());
        if (customer.getEntrepreneurshipForm().taxPaymentForm().getTaxRate().equals(TaxRate.LUMP_SUM2))
        {
            return new Money(costs.amount()
                    .subtract(countHealthInsuranceContributionService
                            .CalculateHealthInsuranceContribution(customer, yearMonth)
                            .amount()),
                    Currency.PLN);
        }
        return costs;
    }
}
