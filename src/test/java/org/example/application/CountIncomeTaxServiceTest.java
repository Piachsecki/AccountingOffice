package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.InMemoryInvoiceRepo;
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
import java.time.YearMonth;

class CountIncomeTaxServiceTest {
    private CountMonthlyMonthlyRevenueService countMonthlyRevenueService;
    private CountIncomeTaxService countIncomeTaxService;
    private CountMonthlyCostsService countMonthlyCostsService;
    private InsertInvoiceService insertInvoiceService;
    private CountHealthInsuranceContributionService countHealthInsuranceContributionService;

    @BeforeEach
    void setUp() {
        InMemoryInvoiceRepo invoiceRepo = new InMemoryInvoiceRepo();
        insertInvoiceService = new InsertInvoiceService(new InsertCostInvoiceService(invoiceRepo), new InsertIncomeInvoiceService(invoiceRepo));
        countMonthlyCostsService = new CountMonthlyCostsService(invoiceRepo);
        countMonthlyRevenueService = new CountMonthlyMonthlyRevenueService(invoiceRepo);
        countIncomeTaxService = new CountIncomeTaxService(countMonthlyRevenueService, countMonthlyCostsService);
        countHealthInsuranceContributionService = new CountHealthInsuranceContributionService(countMonthlyRevenueService);


        Assertions.assertNotNull(invoiceRepo);
        Assertions.assertNotNull(insertInvoiceService);
        Assertions.assertNotNull(countMonthlyCostsService);
        Assertions.assertNotNull(countMonthlyRevenueService);
        Assertions.assertNotNull(countIncomeTaxService);
        Assertions.assertNotNull(countHealthInsuranceContributionService);
    }

    @Test
    void countIncomeTaxForGeneralTax1stScale() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new GeneralTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(
                                BigDecimal.valueOf(4000),
                                Currency.PLN
                        )
                )
                .withCustomer(customer1);
        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());

        //when
        insertInvoiceService.insertInvoice(incomeInvoice1);

        BigDecimal costs = countMonthlyCostsService.countMonthlyCosts(customer1, date).amount();
        BigDecimal revenue = countMonthlyRevenueService.countMonthlyRevenue(customer1.getCustomerId(), date).amount();
        BigDecimal expected = revenue.subtract(costs).multiply(BigDecimal.valueOf(0.12));

        BigDecimal result = countIncomeTaxService.countIncomeTax(customer1, date).amount();

        //then

        Assertions.assertEquals(expected, result);


    }

    @Test
    void countIncomeTaxForGeneralTaxFor2ndScale() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new GeneralTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(
                                BigDecimal.valueOf(8000),
                                Currency.PLN
                        )
                )
                .withCustomer(customer1);
        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());

        //when
        insertInvoiceService.insertInvoice(incomeInvoice1);

        BigDecimal costs = countMonthlyCostsService.countMonthlyCosts(customer1, date).amount();
        BigDecimal revenue = countMonthlyRevenueService.countMonthlyRevenue(customer1.getCustomerId(), date).amount();
        BigDecimal expected = revenue.subtract(costs).multiply(BigDecimal.valueOf(0.32));

        BigDecimal result = countIncomeTaxService.countIncomeTax(customer1, date).amount();

        //then

        Assertions.assertEquals(expected, result);


    }


    @Test
    void countIncomeTaxForFlatTax() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new FlatTax()
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(
                                BigDecimal.valueOf(4000),
                                Currency.PLN
                        )
                )
                .withCustomer(customer1);
        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());

        //when
        insertInvoiceService.insertInvoice(incomeInvoice1);


        BigDecimal costs = countMonthlyCostsService.countMonthlyCosts(customer1, date).amount();
        BigDecimal revenue = countMonthlyRevenueService.countMonthlyRevenue(customer1.getCustomerId(), date).amount();
        BigDecimal expected = revenue.subtract(costs).multiply(BigDecimal.valueOf(0.19));

        BigDecimal result = countIncomeTaxService.countIncomeTax(customer1, date).amount();

        //then
        Assertions.assertEquals(expected, result);


    }


    @Test
    void countIncomeTaxForLumpSum() {
        //given
        Customer customer1 = DataCreator.createCustomer1()
                .withEntrepreneurshipForm(new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ));
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1()
                .withAmount(new Money(
                                BigDecimal.valueOf(8000),
                                Currency.PLN
                        )
                )
                .withCustomer(customer1);
        YearMonth date = YearMonth.of(incomeInvoice1.getDate().getYear(), incomeInvoice1.getDate().getMonth());
        String lumpSumTaxRate = customer1.getEntrepreneurshipForm().taxPaymentForm().getTaxRate().getValue();

        //when
        insertInvoiceService.insertInvoice(incomeInvoice1);

        BigDecimal costs = countMonthlyCostsService.countMonthlyCosts(customer1, date).amount();
        BigDecimal revenue = countMonthlyRevenueService.countMonthlyRevenue(customer1.getCustomerId(), date).amount();

        BigDecimal expected = revenue.subtract(costs)
                .subtract(
                        countHealthInsuranceContributionService
                                .calculateHealthInsuranceContribution(customer1, date)
                                .amount())
                .multiply(new BigDecimal(lumpSumTaxRate));

        BigDecimal result = countIncomeTaxService.countIncomeTax(customer1, date).amount();

        //then
        Assertions.assertEquals(expected, result);


    }


}