package org.example.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.application.exceptions.CountIncomeTaxException;
import org.example.domain.customer.Customer;
import org.example.domain.customer.TaxPayments.FlatTax;
import org.example.domain.customer.TaxPayments.GeneralTax;
import org.example.domain.customer.TaxPayments.LumpSumTax;
import org.example.domain.customer.TaxPayments.TaxPaymentForm;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountIncomeTaxUseCase;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.example.Data.*;
import static org.example.domain.customer.TaxPayments.FlatTax.FLAT_TAX_RATE;


@AllArgsConstructor
@Slf4j
public class CountIncomeTaxService implements CountIncomeTaxUseCase {
    private CountMonthlyMonthlyRevenueService countMonthlyRevenueService;
    private CountMonthlyCostsService countMonthlyCostsService;

    @Override
    public Money countIncomeTax(Customer customer, YearMonth yearMonth) {
        Money monthlyRevenue = countMonthlyRevenueService.countMonthlyRevenue(customer.getCustomerId(), yearMonth);
        Money monthlyCosts = countMonthlyCostsService.countMonthlyCosts(customer, yearMonth);
        BigDecimal incomeBeforeTax = monthlyRevenue.amount().subtract(monthlyCosts.amount());


        Class<? extends TaxPaymentForm> customerTaxPaymentForm = getCustomerTaxPaymentForm(customer);
        if (FlatTax.class.equals(customerTaxPaymentForm)) {
            return new Money(
                    incomeBeforeTax
                            .multiply(FLAT_TAX_RATE),
                    Currency.PLN);
        }else if (GeneralTax.class.equals(customerTaxPaymentForm)){
            if(incomeBeforeTax.compareTo(GeneralTax.SCALE_SALARY) <= 0){
                return new Money(
                        incomeBeforeTax
                                .multiply(new BigDecimal(GeneralTax.FIRST_SCALE)),
                        Currency.PLN);
            }else {
                return new Money(
                        incomeBeforeTax
                                .multiply(new BigDecimal(GeneralTax.SECOND_SCALE)),
                        Currency.PLN);
            }
        }else if (LumpSumTax.class.equals(customerTaxPaymentForm)) {
            return countIncomeTaxForLumpSum(customer, monthlyRevenue, incomeBeforeTax);

        }


            log.error("Cannot calculate incomeTax for [{}]", customer);
            throw new CountIncomeTaxException(
                    String.format("Cannot calculate incomeTax for [%s]", customer.getCustomerId())
            );

        }

    private static Money countIncomeTaxForLumpSum(Customer customer, Money monthlyRevenue, BigDecimal incomeBeforeTax) {
        BigDecimal healthContributionToBeSubtractedFromRevenue = BigDecimal.ZERO;
        BigDecimal amount = monthlyRevenue.amount();

        //do 5k
        if (amount.compareTo(FIRST_CONTRIBUTION_RANGE) < 0) {
            healthContributionToBeSubtractedFromRevenue =
                    healthContributionToBeSubtractedFromRevenue
                            .add(
                                    amount
                                            .multiply(BELOW_CONTRIBUTION_HEALTH_RATE)
                    .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE)) ;

        }
        // 5k - 25k
        else if (
                amount.compareTo(FIRST_CONTRIBUTION_RANGE) >= 0 &&
                        amount.compareTo(SECOND_CONTRIBUTION_RANGE) < 0
        ) {
            healthContributionToBeSubtractedFromRevenue = healthContributionToBeSubtractedFromRevenue
                    .add(amount.multiply(STANDARD_CONTRIBUTION_HEALTH_RATE));
        }
        //powyzej 25k
        else {

            healthContributionToBeSubtractedFromRevenue = healthContributionToBeSubtractedFromRevenue
                    .add(amount
                            .multiply(OVER_CONTRIBUTION_HEALTH_RATE)
                            .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE)
                    );
        }

        BigDecimal lumpSumTaxRate = new BigDecimal(customer.getEntrepreneurshipForm().taxPaymentForm().getTaxRate().getValue());
        return new Money(
                incomeBeforeTax
                        .subtract(healthContributionToBeSubtractedFromRevenue)
                        .multiply(lumpSumTaxRate),
                Currency.PLN
        );
    }

    private static Class<? extends TaxPaymentForm> getCustomerTaxPaymentForm(Customer customer) {
        return customer.getEntrepreneurshipForm().taxPaymentForm().getClass();
    }


}

