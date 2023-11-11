package org.example.application;

import org.example.domain.customer.Customer;
import org.example.domain.customer.TaxPayments.*;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountIncomeTaxUseCase;

import java.math.BigDecimal;
import java.time.YearMonth;

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
            BigDecimal lumpSumTaxRate = new BigDecimal(customer.getEntrepreneurshipForm().taxPaymentForm().getTaxRate().getValue());
            return new Money(incomeBeforeTax.multiply(lumpSumTaxRate), Currency.PLN);
        }

        return null;
    }
}
