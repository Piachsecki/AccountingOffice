package org.example.application;

import lombok.AllArgsConstructor;
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

import static org.example.application.CountHealthInsuranceContributionService.FIRST_CONTRIBUTION_RANGE;
import static org.example.application.CountHealthInsuranceContributionService.SECOND_CONTRIBUTION_RANGE;

@AllArgsConstructor

public class CountIncomeTaxService implements CountIncomeTaxUseCase {
    private CountMonthlyMonthlyRevenueService countMonthlyRevenueService;
    private CountMonthlyCostsService countMonthlyCostsService;

    @Override
    public Money countIncomeTax(Customer customer, YearMonth yearMonth) {
        Money monthlyRevenue = countMonthlyRevenueService.countMonthlyRevenue(customer.getCustomerId(), yearMonth);
        Money monthlyCosts = countMonthlyCostsService.countMonthlyCosts(customer, yearMonth);
        BigDecimal incomeBeforeTax = monthlyRevenue.amount().subtract(monthlyCosts.amount());


        Class<? extends TaxPaymentForm> customerTaxPaymentForm = customer.getEntrepreneurshipForm().taxPaymentForm().getClass();
        if (FlatTax.class.equals(customerTaxPaymentForm)) {
            return new Money(incomeBeforeTax.multiply(BigDecimal.valueOf(0.19)), Currency.PLN);
        }else if (GeneralTax.class.equals(customerTaxPaymentForm)){
            if(incomeBeforeTax.compareTo(GeneralTax.SCALE_SALARY) <= 0){
                return new Money(incomeBeforeTax.multiply(new BigDecimal(GeneralTax.FIRST_SCALE)), Currency.PLN);
            }else {
                return new Money(incomeBeforeTax.multiply(new BigDecimal(GeneralTax.SECOND_SCALE)), Currency.PLN);
            }
        } else if (LumpSumTax.class.equals(customerTaxPaymentForm)) {
            BigDecimal healthContributionToBeSubtractedFromRevenue = BigDecimal.ZERO;
            BigDecimal amount = monthlyRevenue.amount();

            //do 5k
                if (amount.compareTo(BigDecimal.valueOf(FIRST_CONTRIBUTION_RANGE)) < 0) {
                    healthContributionToBeSubtractedFromRevenue =
                            healthContributionToBeSubtractedFromRevenue
                                    .add(
                                            amount
                                                    .multiply(BigDecimal.valueOf(0.6))
                            .multiply(BigDecimal.valueOf(0.09))) ;

                }
                // 5k - 25k
                else if (
                        amount.compareTo(BigDecimal.valueOf(FIRST_CONTRIBUTION_RANGE)) >= 0 &&
                                amount.compareTo(BigDecimal.valueOf(SECOND_CONTRIBUTION_RANGE)) < 0
                ) {
                    healthContributionToBeSubtractedFromRevenue= healthContributionToBeSubtractedFromRevenue.add(amount.multiply(BigDecimal.valueOf(0.09)));
                }
                //powyzej 25k
                else {

                    healthContributionToBeSubtractedFromRevenue= healthContributionToBeSubtractedFromRevenue.add(
                            amount.multiply(BigDecimal.valueOf(1.8)).multiply(BigDecimal.valueOf(0.09)));
                }

            BigDecimal lumpSumTaxRate = new BigDecimal(customer.getEntrepreneurshipForm().taxPaymentForm().getTaxRate().getValue());
            return new Money(
                    incomeBeforeTax
                    .subtract(healthContributionToBeSubtractedFromRevenue)
                    .multiply(lumpSumTaxRate),
                    Currency.PLN
            );

            }




            return null;
        }


    }

