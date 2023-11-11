package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.InMemoryInvoiceRepo;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountMonthlyCostsUseCase;
import org.example.port.in.invoice.CountMonthlyRevenueUseCase;
import org.example.port.out.InvoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.YearMonth;

public class CountMonthlyCostsServiceTest {
    private InvoiceRepository invoiceRepository;
    private CountHealthInsuranceContributionService countHealthInsuranceContributionService;
    private CountMonthlyCostsService countMonthlyCostsService;



    @BeforeEach
    void setUp(){
        invoiceRepository = new InMemoryInvoiceRepo();
        CountMonthlyMonthlyRevenueService countMonthlyMonthlyRevenueService = new CountMonthlyMonthlyRevenueService();
        countHealthInsuranceContributionService = new CountHealthInsuranceContributionService(countMonthlyMonthlyRevenueService);
        countMonthlyCostsService = new CountMonthlyCostsService(
                invoiceRepository,
                countHealthInsuranceContributionService
        );

    }


    @Test
    void checkIfCountsCostsGoodForFlatTax(){
        //given

        Customer customer = DataCreator.createCustomer1();
        CostInvoice costInvoice1 = DataCreator.createCostInvoice1().withCustomer(customer);
        CostInvoice costInvoice2 = DataCreator.createCostInvoice2().withCustomer(customer);
        CostInvoice costInvoice3 = DataCreator.createCostInvoice3().withCustomer(customer);
        CostInvoice costInvoice4 = DataCreator.createCostInvoice4().withCustomer(customer);
        CostInvoice costInvoice5 = DataCreator.createCostInvoice5().withCustomer(customer);
        YearMonth yearMonth = YearMonth.of(2022, 10);


        //when
        invoiceRepository.insertInvoice(costInvoice1);
        invoiceRepository.insertInvoice(costInvoice2);
        invoiceRepository.insertInvoice(costInvoice3);
        invoiceRepository.insertInvoice(costInvoice4);
        invoiceRepository.insertInvoice(costInvoice5);

        BigDecimal result = countMonthlyCostsService.countMonthlyCosts(customer, yearMonth).amount();
        BigDecimal expected = costInvoice1.getAmount().amount();

        //then
        Assertions.assertEquals(expected, result);
    }



    @Test
    void checkIfCountsCostsGoodForLumpSum(){

    }



    @Test
    void checkIfCountsCostsGoodForGeneralTax(){

    }

}
