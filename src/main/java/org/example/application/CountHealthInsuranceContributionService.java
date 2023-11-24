package org.example.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.application.exceptions.CountHealthInsuranceContributionException;
import org.example.domain.customer.Customer;
import org.example.domain.customer.TaxPayments.FlatTax;
import org.example.domain.customer.TaxPayments.GeneralTax;
import org.example.domain.customer.TaxPayments.LumpSumTax;
import org.example.domain.customer.TaxPayments.TaxPaymentForm;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountHealthInsuranceContributionUseCase;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.example.Data.*;

@AllArgsConstructor
@Getter
@Slf4j
public class CountHealthInsuranceContributionService implements CountHealthInsuranceContributionUseCase {
    private CountMonthlyMonthlyRevenueService countMonthlyRevenueService;
    private CountMonthlyIncomeService countIncomeService;

    public CountHealthInsuranceContributionService(CountMonthlyMonthlyRevenueService countMonthlyMonthlyRevenueService) {
        this.countMonthlyRevenueService = countMonthlyMonthlyRevenueService;
    }

    private static boolean checkIfIncomeIsLessThanMinimumWage(Money monthlyIncome) {
        return monthlyIncome.amount().compareTo(MINIMUM_WAGE) <= 0;
    }

    private static boolean checkIncomeRangeForFlatTax(Money monthlyIncome) {
        return monthlyIncome.amount().compareTo(MINIMUM_WAGE) <= 0;
    }

    private static int checkTheRevenueRangeForHealthInsuranceTax(BigDecimal monthlyRevenue) {
        if (monthlyRevenue.compareTo(FIRST_CONTRIBUTION_RANGE) < 0) {
            return 1;
        } else if (monthlyRevenue.compareTo(FIRST_CONTRIBUTION_RANGE) >= 0 &&
                monthlyRevenue.compareTo(SECOND_CONTRIBUTION_RANGE) < 0) {
            return 2;
        } else {
            return 3;
        }

    }

    private static Class<? extends TaxPaymentForm> CustomersTaxPaymentForm(Customer customer) {
        return customer.getEntrepreneurshipForm().taxPaymentForm().getClass();
    }

    @Override
    public Money calculateHealthInsuranceContribution(Customer customer, YearMonth monthToCalculateHealthInsuranceContribution) {
        Class<? extends TaxPaymentForm> taxPaymentForm = CustomersTaxPaymentForm(customer);

        if (LumpSumTax.class.equals(taxPaymentForm)) {
            return countHealthInsuranceForLumpSumTax(customer, monthToCalculateHealthInsuranceContribution);
        }
        if (FlatTax.class.equals(taxPaymentForm)) {
            return countHealthInsuranceForFlatTax(customer, monthToCalculateHealthInsuranceContribution);
        }
        if (GeneralTax.class.equals(taxPaymentForm)) {
            return countHealthInsuranceForGeneralTax(customer, monthToCalculateHealthInsuranceContribution, STANDARD_CONTRIBUTION_HEALTH_RATE);
        }

        log.error("The given taxPaymentForm {} is wrong!", taxPaymentForm);
        throw new CountHealthInsuranceContributionException(
                String.format("Given taxPaymentForm:[%s] is wrong" +
                        "!", taxPaymentForm));
    }

    private Money countHealthInsuranceForGeneralTax(Customer customer, YearMonth monthToCalculateHealthInsuranceContribution, BigDecimal standardContributionHealthRate) {
        Money monthlyIncome = countIncomeService
                .countMonthlyIncome(
                        customer,
                        monthToCalculateHealthInsuranceContribution
                );
        if (checkIfIncomeIsLessThanMinimumWage(monthlyIncome)) {
            return new Money(
                    MINIMUM_WAGE.multiply(STANDARD_CONTRIBUTION_HEALTH_RATE),
                    Currency.PLN
            );

        } else {
            return new Money(
                    monthlyIncome.amount()
                            .multiply(standardContributionHealthRate),
                    Currency.PLN);
        }
    }

    private Money countHealthInsuranceForFlatTax(Customer customer, YearMonth monthToCalculateHealthInsuranceContribution) {
        Money monthlyIncome = countIncomeService.countMonthlyIncome(
                customer,
                monthToCalculateHealthInsuranceContribution
        );
        if (checkIncomeRangeForFlatTax(monthlyIncome)) {
            return new Money(
                    MINIMUM_WAGE
                            .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE)
                    , Currency.PLN
            );

        } else {
            return new Money(
                    monthlyIncome
                            .amount()
                            .multiply(
                                    CONTRIBUTION_HEALTH_RATE_FOR_FLAT_TAX),
                    Currency.PLN
            );
        }
    }

    private Money countHealthInsuranceForLumpSumTax(Customer customer, YearMonth monthToCalculateHealthInsuranceContribution) {
        BigDecimal monthlyRevenue = countMonthlyRevenueService.countMonthlyRevenue(
                        customer.getCustomerId(),
                        monthToCalculateHealthInsuranceContribution
                )
                .amount();

        switch (checkTheRevenueRangeForHealthInsuranceTax(monthlyRevenue)) {
            //1st range do 5k
            case 1 -> {
                return new Money(
                        monthlyRevenue
                                .multiply(BELOW_CONTRIBUTION_HEALTH_RATE)
                                .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE)
                        , Currency.PLN);
            }
            //second range 5-25k
            case 2 -> {
                return new Money(
                        monthlyRevenue
                                .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE),
                        Currency.PLN);

            }
            //3rd range powyzej 25k
            case 3 -> {
                return new Money(
                        monthlyRevenue
                                .multiply(OVER_CONTRIBUTION_HEALTH_RATE)
                                .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE),
                        Currency.PLN);
            }
        }

        log.error("Cannot calculate the insurance for LumpSumTax!");
        throw new CountHealthInsuranceContributionException(
                "Cannot calculate the insurance for LumpSumTax!"
        );

    }
}
