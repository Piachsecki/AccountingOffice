package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.maps.InMemoryInvoiceRepo;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.Invoice;
import org.example.port.in.invoice.InsertCostInvoiceUseCase;
import org.example.port.out.InvoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.UUID;

class InsertCostInvoiceServiceTest {

    private InsertCostInvoiceUseCase insertCostInvoiceService;
    private InvoiceRepository invoiceRepository;


    @BeforeEach
    void setUp(){
        this.invoiceRepository = new InMemoryInvoiceRepo();
        this.insertCostInvoiceService =new InsertCostInvoiceService(invoiceRepository);
    }
    @Test
    void checkIfCostInvoicesAreAddedToUserCorrectly() {
        //given
        Customer customer1 = DataCreator.createCustomer1();
        UUID customerId = customer1.getCustomerId();
        CostInvoice costInvoice1 = DataCreator.createCostInvoice1().withCustomer(customer1);
        CostInvoice costInvoice2 = DataCreator.createCostInvoice2().withCustomer(customer1);
        CostInvoice costInvoice3 = DataCreator.createCostInvoice3().withCustomer(customer1);
        CostInvoice costInvoice4 = DataCreator.createCostInvoice4().withCustomer(customer1);
        CostInvoice costInvoice5 = DataCreator.createCostInvoice5().withCustomer(customer1);


        //when
        insertCostInvoiceService.insertCostInvoice(customerId, costInvoice1);
        insertCostInvoiceService.insertCostInvoice(customerId, costInvoice2);
        insertCostInvoiceService.insertCostInvoice(customerId, costInvoice3);
        insertCostInvoiceService.insertCostInvoice(customerId, costInvoice4);
        insertCostInvoiceService.insertCostInvoice(customerId, costInvoice5);
        HashSet<Invoice> invoices = invoiceRepository.listAllInvoicesForCustomerId(customer1.getCustomerId());


        //then
        Assertions.assertEquals(5,invoiceRepository.listAllInvoicesForCustomerId(customer1.getCustomerId()).size());
        Assertions.assertEquals(5, customer1.getCostInvoices().size());
        for (Invoice invoice : invoices) {
            Assertions.assertEquals(CostInvoice.class, invoice.getClass());
        }
        Assertions.assertEquals(CostInvoice.class, customer1.getCostInvoices().get(0).getClass());

    }

}