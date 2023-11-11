package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.customer.Customer;
import org.example.domain.customer.TaxPayments.FlatTax;
import org.example.domain.customer.TaxPayments.GeneralTax;
import org.example.domain.customer.TaxPayments.LumpSumTax;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountHealthInsuranceContributionUseCase;

import java.math.BigDecimal;
import java.time.YearMonth;

@AllArgsConstructor
public class CountHealthInsuranceContributionService implements CountHealthInsuranceContributionUseCase {
    private static final Integer FIRST_CONTRIBUTION_RANGE = 5000;
    private static final Integer SECOND_CONTRIBUTION_RANGE = 25000;
    private static final BigDecimal MINIMUM_WAGE = BigDecimal.valueOf(3490);
    private CountMonthlyMonthlyRevenueService countMonthlyRevenueService;
    private CountMonthlyIncomeService countIncomeService;

    public CountHealthInsuranceContributionService(CountMonthlyMonthlyRevenueService countMonthlyMonthlyRevenueService) {
        this.countMonthlyRevenueService = countMonthlyMonthlyRevenueService;
    }

    @Override
    public Money calculateHealthInsuranceContribution(Customer customer, YearMonth monthToCalculateHealthInsuranceContribution) {

        if (FlatTax.class.equals(customer.getEntrepreneurshipForm().taxPaymentForm().getClass())) {
            Money monthlyIncome = countIncomeService.countMonthlyIncome(
                    customer,
                    monthToCalculateHealthInsuranceContribution
            );
            if (monthlyIncome.amount().compareTo(MINIMUM_WAGE) <= 0) {
                return new Money(MINIMUM_WAGE.multiply(BigDecimal.valueOf(0.09)), Currency.PLN);

            } else {
                return new Money(monthlyIncome.amount().multiply(BigDecimal.valueOf(0.49)), Currency.PLN);
            }

        }


        if (GeneralTax.class.equals(customer.getEntrepreneurshipForm().taxPaymentForm().getClass())) {
            Money monthlyIncome = countIncomeService.countMonthlyIncome(
                    customer,
                    monthToCalculateHealthInsuranceContribution
            );
            if (monthlyIncome.amount().compareTo(MINIMUM_WAGE) <= 0) {
                return new Money(MINIMUM_WAGE.multiply(BigDecimal.valueOf(0.09)), Currency.PLN);

            } else {
                return new Money(monthlyIncome.amount().multiply(BigDecimal.valueOf(0.09)), Currency.PLN);
            }

        }


        if (LumpSumTax.class.equals(customer.getEntrepreneurshipForm().taxPaymentForm().getClass())) {
            BigDecimal monthlyRevenue = countMonthlyRevenueService.countMonthlyRevenue(
                            customer.getCustomerId(),
                            monthToCalculateHealthInsuranceContribution
                    )
                    .amount();

            //do 5k
            if (monthlyRevenue.compareTo(BigDecimal.valueOf(FIRST_CONTRIBUTION_RANGE)) < 0) {
                return new Money(
                        monthlyRevenue
                                .multiply(BigDecimal.valueOf(0.6f))
                                .multiply(BigDecimal.valueOf(0.09f))
                        , Currency.PLN);
            }
            // 5k - 25k
            else if (
                    monthlyRevenue.compareTo(BigDecimal.valueOf(FIRST_CONTRIBUTION_RANGE)) >= 0 &&
                            monthlyRevenue.compareTo(BigDecimal.valueOf(SECOND_CONTRIBUTION_RANGE)) < 0
            ) {
                return new Money(monthlyRevenue.multiply(BigDecimal.valueOf(0.09)), Currency.PLN);
            }
            //powyzej 25k
            else {
                return new Money(
                        monthlyRevenue
                                .multiply(BigDecimal.valueOf(1.8))
                                .multiply(BigDecimal.valueOf(0.09)),
                        Currency.PLN);
            }

        }

        return null;
    }
}
