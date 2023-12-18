package org.example.domain.customer;

import org.example.DataCreator;
import org.example.domain.customer.TaxPayments.FlatTax;
import org.example.domain.customer.TaxPayments.GeneralTax;
import org.example.domain.customer.TaxPayments.IndustryType;
import org.example.domain.customer.TaxPayments.LumpSumTax;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.example.Data.*;
import static org.example.domain.customer.TaxPayments.FlatTax.FLAT_TAX_RATE;
import static org.example.domain.customer.TaxPayments.GeneralTax.FIRST_SCALE;
import static org.example.domain.customer.TaxPayments.GeneralTax.SECOND_SCALE;

@ExtendWith(MockitoExtension.class)
public class CustomerTest {


    @Test
    void shouldCalculateIncomeForFlatTaxCorrectly() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new GeneralTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(6000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal revenue = customer1.countMonthlyRevenue(date).amount();
        BigDecimal costs = customer1.countMonthlyCosts(date).amount();
        BigDecimal incomeTaxToPay = customer1.countIncomeTax(date).amount();

        BigDecimal expected = revenue.subtract(costs).subtract(incomeTaxToPay);
        BigDecimal result = customer1.countMonthlyIncome(date).amount();

        //then
        Assertions.assertEquals(expected, result);


    }


    @Test
    void shouldCalculateIncomeForGeneralTaxCorrectly() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new FlatTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(6000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal revenue = customer1.countMonthlyRevenue(date).amount();
        BigDecimal costs = customer1.countMonthlyCosts(date).amount();
        BigDecimal incomeTaxToPay = customer1.countIncomeTax(date).amount();

        BigDecimal expected = revenue.subtract(costs).subtract(incomeTaxToPay);
        BigDecimal result = customer1.countMonthlyIncome(date).amount();

        //then
        Assertions.assertEquals(expected, result);


    }

    @Test
    void shouldCalculateIncomeForLumpSumTaxCorrectly() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(6000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal revenue = customer1.countMonthlyRevenue(date).amount();
        BigDecimal costs = customer1.countMonthlyCosts(date).amount();
        BigDecimal incomeTaxToPay = customer1.countIncomeTax(date).amount();

        BigDecimal expected = revenue.subtract(costs).subtract(incomeTaxToPay);
        BigDecimal result = customer1.countMonthlyIncome(date).amount();

        //then
        Assertions.assertEquals(expected, result);


    }


    @Test
    void shouldCalculateLumpSumTaxCorrectly() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(6000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal revenue = customer1.countMonthlyRevenue(date).amount();
        BigDecimal healthContribution = customer1.calculateHealthInsuranceContribution(date).amount();

        BigDecimal expected =
                revenue
                        .subtract(healthContribution)
                        .subtract(SOCIAL_CONTRIBUTION)
                        .multiply(
                                new BigDecimal(
                                        customer1
                                                .getEntrepreneurshipForm()
                                                .taxPaymentForm()
                                                .getTaxRate()
                                                .getValue())
                        );
        BigDecimal result = customer1.countIncomeTax(date).amount();

        //then
        Assertions.assertEquals(expected, result);


    }


    @Test
    void shouldCalculate1stScaleGeneralTaxCorrectly() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new GeneralTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(4000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal revenue = customer1.countMonthlyRevenue(date).amount();
        BigDecimal costs = customer1.countMonthlyCosts(date).amount();

        BigDecimal expected =
                revenue
                        .subtract(costs)
                        .subtract(SOCIAL_CONTRIBUTION)
                        .multiply(new BigDecimal(FIRST_SCALE));
        BigDecimal result = customer1.countIncomeTax(date).amount();

        //then
        Assertions.assertEquals(expected, result);


    }

    @Test
    void shouldCalculate2ndScaleGeneralTaxCorrectly() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new GeneralTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(20000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal revenue = customer1.countMonthlyRevenue(date).amount();
        BigDecimal costs = customer1.countMonthlyCosts(date).amount();

        BigDecimal expected =
                revenue
                        .subtract(costs)
                        .subtract(SOCIAL_CONTRIBUTION)
                        .multiply(new BigDecimal(SECOND_SCALE));
        BigDecimal result = customer1.countIncomeTax(date).amount();

        //then
        Assertions.assertEquals(expected, result);


    }


    @Test
    void shouldCalculateFlatTaxCorrectly() {
//given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new FlatTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(4000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal revenue = customer1.countMonthlyRevenue(date).amount();
        BigDecimal costs = customer1.countMonthlyCosts(date).amount();

        BigDecimal expected =
                revenue
                        .subtract(costs)
                        .subtract(SOCIAL_CONTRIBUTION)
                        .multiply(FLAT_TAX_RATE);
        BigDecimal result = customer1.countIncomeTax(date).amount();

        //then
        Assertions.assertEquals(expected, result);

    }


    @Test
    void shouldCalculateHealthInsuranceContributionForGeneralTaxCorrectly() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new GeneralTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(4000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal income = customer1.countMonthlyIncome(date).amount();

        BigDecimal expected =
                income

                        .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE);
        BigDecimal result = customer1.calculateHealthInsuranceContribution(date).amount();

        //then
        Assertions.assertEquals(expected, result);

    }


    @Test
    void shouldCalculateHealthInsuranceContributionForFlatTaxCorrectly() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new FlatTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(4000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal income = customer1.countMonthlyIncome(date).amount();

        BigDecimal expected =
                income
                        .multiply(CONTRIBUTION_HEALTH_RATE_FOR_FLAT_TAX);
        BigDecimal result = customer1.calculateHealthInsuranceContribution(date).amount();

        //then
        Assertions.assertEquals(expected, result);


    }


    @Test
    void shouldCalculateHealthInsuranceContributionForLumpSumTax1stCorrectly() {

        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(4000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal revenue = customer1.countMonthlyRevenue(date).amount();

        BigDecimal result = customer1.calculateHealthInsuranceContribution(date).amount();
        BigDecimal expected =
                revenue
                        .multiply(BELOW_CONTRIBUTION_HEALTH_RATE)
                        .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE);

        //then
        Assertions.assertEquals(expected, result);

    }



    @Test
    void shouldCalculateHealthInsuranceContributionForLumpSumTax2ndCorrectly() {

        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(8000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal revenue = customer1.countMonthlyRevenue(date).amount();

        BigDecimal result = customer1.calculateHealthInsuranceContribution(date).amount();
        BigDecimal expected =
                revenue
                        .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE);

        //then
        Assertions.assertEquals(expected, result);

    }



    @Test
    void shouldCalculateHealthInsuranceContributionForLumpSumTax3rdCorrectly() {

        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(40000), Currency.PLN))
                .withCustomer(customer1);

        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());


        //when
        customer1.insertIncomeInvoiceToCustomer(incomeInvoice1);
        BigDecimal revenue = customer1.countMonthlyRevenue(date).amount();

        BigDecimal result = customer1.calculateHealthInsuranceContribution(date).amount();
        BigDecimal expected =
                revenue
                        .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE)
                        .multiply(OVER_CONTRIBUTION_HEALTH_RATE);

        //then
        Assertions.assertEquals(expected, result);

    }






}
