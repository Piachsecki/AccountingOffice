package org.example.integration;

import org.example.DataCreator;
import org.example.adapter.out.InMemoryInvoiceRepo;
import org.example.application.*;
import org.example.domain.customer.Customer;
import org.example.domain.customer.Entrepreneurship;
import org.example.domain.customer.EntrepreneurshipForm;
import org.example.domain.customer.TaxPayments.FlatTax;
import org.example.domain.customer.TaxPayments.GeneralTax;
import org.example.domain.customer.TaxPayments.IndustryType;
import org.example.domain.customer.TaxPayments.LumpSumTax;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;

import static org.example.Data.MINIMUM_WAGE;

class CountHealthInsuranceContributionServiceTest {
    private CountHealthInsuranceContributionService countHealthInsuranceContributionService;
    private CountMonthlyMonthlyRevenueService countMonthlyRevenueService;
    private CountMonthlyIncomeService countIncomeService;
    private CountMonthlyCostsService countMonthlyCostsService;
    private InsertIncomeInvoiceService insertIncomeInvoice;
    private CountIncomeTaxService countIncomeTaxService;
    private InMemoryInvoiceRepo invoiceRepo;
    private InsertCostInvoiceService insertCostInvoice;
    private InsertInvoiceService insertInvoiceService;



    @BeforeEach
    void setUp(){
        invoiceRepo = new InMemoryInvoiceRepo();
        countMonthlyRevenueService = new CountMonthlyMonthlyRevenueService(invoiceRepo);
        countMonthlyCostsService = new CountMonthlyCostsService(invoiceRepo);
        countIncomeTaxService = new CountIncomeTaxService(countMonthlyRevenueService, countMonthlyCostsService);
        countIncomeService = new CountMonthlyIncomeService(countMonthlyRevenueService,countMonthlyCostsService, countIncomeTaxService);
        countHealthInsuranceContributionService = new CountHealthInsuranceContributionService(countMonthlyRevenueService, countIncomeService);
        insertIncomeInvoice = new InsertIncomeInvoiceService(invoiceRepo);
        insertCostInvoice = new InsertCostInvoiceService(invoiceRepo);
        insertInvoiceService = new InsertInvoiceService(insertCostInvoice, insertIncomeInvoice);


        Assertions.assertNotNull(invoiceRepo);
        Assertions.assertNotNull(countMonthlyRevenueService);
        Assertions.assertNotNull(countMonthlyCostsService);
        Assertions.assertNotNull(countIncomeTaxService);
        Assertions.assertNotNull(countIncomeService);
        Assertions.assertNotNull(countHealthInsuranceContributionService);
        Assertions.assertNotNull(insertIncomeInvoice);
    }


    @Test
    void shouldCalculateHealthInsuranceContributionCorrectlyForFlatTax() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(
                        new Entrepreneurship(
                                EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                                new FlatTax()
                        ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1().withCustomer(customer1);


        //when
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice1);
        BigDecimal result = countHealthInsuranceContributionService.calculateHealthInsuranceContribution(customer1, YearMonth.of(2020, 10)).amount();
        BigDecimal expected = BigDecimal.valueOf(0.49).multiply(countIncomeService.countMonthlyIncome(customer1, YearMonth.of(2020, 10)).amount());


        //then
        Assertions.assertEquals(expected, result);

    }
    @Test
    void shouldCalculateHealthInsuranceContributionCorrectlyForGeneralTax1() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new GeneralTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(
                        BigDecimal.valueOf(3000),
                                Currency.PLN
                                )
                )
                .withCustomer(customer1);


        //when
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice1);
        Money money = countHealthInsuranceContributionService.calculateHealthInsuranceContribution(customer1, YearMonth.of(2020, 10));
        BigDecimal result = money.amount();
        BigDecimal amount =countIncomeService.countMonthlyIncome(customer1, YearMonth.of(2020, 10)).amount();
        BigDecimal expected = MINIMUM_WAGE.multiply(BigDecimal.valueOf(0.09));


        //then
        Assertions.assertEquals(expected, result);

    }

    @Test
    void shouldCalculateHealthInsuranceContributionCorrectlyForGeneralTax2() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new GeneralTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(
                                BigDecimal.valueOf(6000),
                                Currency.PLN
                        )
                )
                .withCustomer(customer1);


        //when
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice1);
        Money money = countHealthInsuranceContributionService.calculateHealthInsuranceContribution(customer1, YearMonth.of(2020, 10));
        BigDecimal result = money.amount();
        BigDecimal amount = countIncomeService.countMonthlyIncome(customer1, YearMonth.of(2020, 10)).amount();
        BigDecimal expected = amount.multiply(BigDecimal.valueOf(0.09));

        //then
        Assertions.assertEquals(expected, result);

    }


    @Test
    void shouldCalculateHealthInsuranceContributionCorrectlyForLumpSum1stScale() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(
                        new Money(BigDecimal.valueOf(4000), Currency.PLN)
                )
                .withCustomer(customer1);


        //when
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice1);
        BigDecimal result = countHealthInsuranceContributionService.calculateHealthInsuranceContribution(customer1, YearMonth.of(2020, 10)).amount().setScale(3,RoundingMode.FLOOR);
        BigDecimal amount = countMonthlyRevenueService.countMonthlyRevenue(customer1.getCustomerId(), YearMonth.of(2020, 10)).amount();
        BigDecimal expected = BigDecimal.valueOf(0.09).multiply(amount).multiply(BigDecimal.valueOf(0.6));


        //then
        Assertions.assertEquals(expected, result);

    }


    @Test
    void shouldCalculateHealthInsuranceContributionCorrectlyForLumpSum2ndScale() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ));

        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withCustomer(customer1);
        IncomeInvoice incomeInvoice2 = DataCreator.createIncomeInvoice2()
                .withAmount(new Money(
                        new BigDecimal(8000),
                        Currency.PLN
                ))
                .withCustomer(customer1);


        //when
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice1);
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice2);
        Assertions.assertEquals(customer1.getIncomeInvoices().size(), invoiceRepo.getInvoices().get(customer1.getCustomerId()).size() );

        BigDecimal amount = countMonthlyRevenueService.countMonthlyRevenue(customer1.getCustomerId(), YearMonth.of(2020, 10)).amount();
        Money money = countHealthInsuranceContributionService.calculateHealthInsuranceContribution(customer1, YearMonth.of(2020, 10));
        BigDecimal result = money.amount();
        BigDecimal expected = BigDecimal.valueOf(0.09).multiply(amount);


        //then
        Assertions.assertEquals(expected, result);

    }


    @Test
    void shouldCalculateHealthInsuranceContributionCorrectlyForLumpSum3rdScale() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(BigDecimal.valueOf(8000), Currency.PLN))
                .withCustomer(customer1);
        IncomeInvoice incomeInvoice2 = DataCreator.createIncomeInvoice2()
                .withAmount(new Money(BigDecimal.valueOf(8000), Currency.PLN))
                .withCustomer(customer1);
        IncomeInvoice incomeInvoice3 = DataCreator.createIncomeInvoice3()
                .withAmount(new Money(BigDecimal.valueOf(8000), Currency.PLN))
                .withCustomer(customer1);
        IncomeInvoice incomeInvoice4 = DataCreator.createIncomeInvoice4()
                .withAmount(new Money(BigDecimal.valueOf(8000), Currency.PLN))
                .withCustomer(customer1);
        IncomeInvoice incomeInvoice5 = DataCreator.createIncomeInvoice5()
                .withAmount(new Money(BigDecimal.valueOf(8000), Currency.PLN))
                .withCustomer(customer1);


        //when
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice1);
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice2);
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice3);
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice4);
        insertIncomeInvoice.insertIncomeInvoice(incomeInvoice5);
        Assertions.assertEquals(customer1.getIncomeInvoices().size(), invoiceRepo.getInvoices().get(customer1.getCustomerId()).size() );
        BigDecimal result = countHealthInsuranceContributionService.calculateHealthInsuranceContribution(customer1, YearMonth.of(2020, 10)).amount();
        BigDecimal amount = countMonthlyRevenueService.countMonthlyRevenue(customer1.getCustomerId(), YearMonth.of(2020, 10)).amount();
        BigDecimal expected = BigDecimal.valueOf(0.09).multiply(amount).multiply(BigDecimal.valueOf(1.8));


        //then
        Assertions.assertEquals(expected, result);

    }

}