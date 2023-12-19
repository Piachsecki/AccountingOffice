package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.maps.InMemoryInvoiceRepo;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.port.out.InvoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class InsertBothTypeOfInvoicesTest {

    private InsertIncomeInvoiceService insertIncomeInvoiceService;
    private InsertCostInvoiceService insertCostInvoiceService;
    private InvoiceService invoiceService;


    @BeforeEach
    void setUp() {
        InvoiceRepository invoiceRepository;
        invoiceRepository = new InMemoryInvoiceRepo();
        insertIncomeInvoiceService = new InsertIncomeInvoiceService(invoiceRepository);
        insertCostInvoiceService = new InsertCostInvoiceService(invoiceRepository);
        invoiceService = new InvoiceService(invoiceRepository, insertCostInvoiceService, insertIncomeInvoiceService);
    }


    @Test
    void checkIfBothTypesOfInvoicesAreAddedToTheSameRepo() {
        //given
        Customer customer1 = DataCreator.createCustomer1();
        UUID customerId = customer1.getCustomerId();
        CostInvoice costInvoice1 = DataCreator.createCostInvoice1().withCustomer(customer1);
        CostInvoice costInvoice2 = DataCreator.createCostInvoice2().withCustomer(customer1);
        CostInvoice costInvoice3 = DataCreator.createCostInvoice3().withCustomer(customer1);
        CostInvoice costInvoice4 = DataCreator.createCostInvoice4().withCustomer(customer1);
        CostInvoice costInvoice5 = DataCreator.createCostInvoice5().withCustomer(customer1);

        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1().withCustomer(customer1);
        IncomeInvoice incomeInvoice2 = DataCreator.createIncomeInvoice2().withCustomer(customer1);
        IncomeInvoice incomeInvoice3 = DataCreator.createIncomeInvoice3().withCustomer(customer1);
        IncomeInvoice incomeInvoice4 = DataCreator.createIncomeInvoice4().withCustomer(customer1);
        IncomeInvoice incomeInvoice5 = DataCreator.createIncomeInvoice5().withCustomer(customer1);


        //when
        insertCostInvoiceService.insertCostInvoice(customerId, costInvoice1);
        insertCostInvoiceService.insertCostInvoice(customerId, costInvoice2);
        insertCostInvoiceService.insertCostInvoice(customerId, costInvoice3);
        insertCostInvoiceService.insertCostInvoice(customerId, costInvoice4);
        insertCostInvoiceService.insertCostInvoice(customerId, costInvoice5);

        insertIncomeInvoiceService.insertIncomeInvoice(customerId, incomeInvoice1);
        insertIncomeInvoiceService.insertIncomeInvoice(customerId, incomeInvoice2);
        insertIncomeInvoiceService.insertIncomeInvoice(customerId, incomeInvoice3);
        insertIncomeInvoiceService.insertIncomeInvoice(customerId, incomeInvoice4);
        insertIncomeInvoiceService.insertIncomeInvoice(customerId, incomeInvoice5);
        //then

        Assertions.assertEquals(10, invoiceService.listAllInvoicesForCustomerId(customer1.getCustomerId()).size());
        Assertions.assertEquals(5, customer1.getCostInvoices().size());
        Assertions.assertEquals(5, customer1.getIncomeInvoices().size());
    }
}
