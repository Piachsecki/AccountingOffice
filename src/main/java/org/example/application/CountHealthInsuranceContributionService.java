package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.customer.Customer;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountHealthInsuranceContributionUseCase;
import org.example.port.out.InvoiceRepository;

import java.math.BigDecimal;
import java.time.YearMonth;

@AllArgsConstructor
public class CountHealthInsuranceContributionService implements CountHealthInsuranceContributionUseCase {
    private CountMonthlyRevenueService countMonthlyRevenueService;
    private CountMonthlyIncomeService countIncomeService;
    private InvoiceRepository invoiceRepository;

    @Override
    public Money CalculateHealthInsuranceContribution(Customer customer, YearMonth monthToCalculateHealthInsuranceContribution) {
        Money moneyFromWhichHealthInsuranceIsCounted = new Money(BigDecimal.ZERO, Currency.PLN);
        switch (customer.getEntrepreneurshipForm().taxPaymentForm().getTaxRate()){
//            case GENERAL_TAX -> countIncomeService.countMonthlyIncome();
//            case FLAT_TAX -> countIncomeService.countMonthlyIncome();
            case LUMP_SUM2 -> countMonthlyRevenueService.countMonthlyRevenueUseCase(
                    customer.getCustomerId(),
                    monthToCalculateHealthInsuranceContribution
            );
        }

//        return invoiceRepository.calculateHealthInsuranceContribution(customer );
        return null;
    }
}
