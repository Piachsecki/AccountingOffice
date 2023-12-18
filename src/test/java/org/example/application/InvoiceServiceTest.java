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

class InvoiceServiceTest {
    private InvoiceService invoiceService;
    private InsertCostInvoiceService insertCostInvoiceService;
    private InsertIncomeInvoiceService insertIncomeInvoiceService;
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    void setUp(){
        invoiceRepository = new InMemoryInvoiceRepo();
        insertCostInvoiceService = new InsertCostInvoiceService(invoiceRepository);
        insertIncomeInvoiceService = new InsertIncomeInvoiceService(invoiceRepository);
        invoiceService = new InvoiceService(invoiceRepository, insertCostInvoiceService, insertIncomeInvoiceService);
    }

    @Test
    void insertInvoice() {
        //given
        Customer customer1 = DataCreator.createCustomer1();
        UUID customerId = customer1.getCustomerId();
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1().withCustomer(customer1);
        CostInvoice costInvoice1 = DataCreator.createCostInvoice1().withCustomer(customer1);

        //when
        invoiceService.insertInvoice(customerId, incomeInvoice1);
        invoiceService.insertInvoice(customerId, costInvoice1);

        //then
        Assertions.assertEquals(2, invoiceRepository.listAllInvoicesForCustomerId(customer1.getCustomerId()).size());
        Assertions.assertEquals(1, customer1.getCostInvoices().size());
        Assertions.assertEquals(IncomeInvoice.class, customer1.getIncomeInvoices().get(0).getClass());
        Assertions.assertEquals(1, customer1.getIncomeInvoices().size());
        Assertions.assertEquals(CostInvoice.class, customer1.getCostInvoices().get(0).getClass());


    }
}