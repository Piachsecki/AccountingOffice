package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.maps.InMemoryInvoiceRepo;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.port.out.InvoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteInvoiceServiceTest {
    private DeleteInvoiceService deleteInvoiceService;
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    void setUp(){
        this.invoiceRepository = new InMemoryInvoiceRepo();
        deleteInvoiceService = new DeleteInvoiceService(invoiceRepository);
    }

    @Test
    void checkIfSingleInvoiceIsDeletedCorrectly(){
        //given
        Customer customer1 = DataCreator.createCustomer1();
        CostInvoice costInvoice1 = DataCreator.createCostInvoice1().withCustomer(customer1);
        CostInvoice costInvoice2 = DataCreator.createCostInvoice2().withCustomer(customer1);
        CostInvoice costInvoice3 = DataCreator.createCostInvoice3().withCustomer(customer1);

        //when
        invoiceRepository.insertInvoice(customer1.getCustomerId(), costInvoice1);
        invoiceRepository.insertInvoice(customer1.getCustomerId(), costInvoice2);
        invoiceRepository.insertInvoice(customer1.getCustomerId(), costInvoice3);
        deleteInvoiceService.deleteInvoice(customer1.getCustomerId(), costInvoice2.getInvoiceId());

        //then
        Assertions.assertEquals(2, invoiceRepository.listAllInvoicesForCustomerId(customer1.getCustomerId()).size());

    }



}
