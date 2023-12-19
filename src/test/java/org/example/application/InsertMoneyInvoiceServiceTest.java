package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.maps.InMemoryInvoiceRepo;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.IncomeInvoice;
import org.example.port.out.InvoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class InsertMoneyInvoiceServiceTest {
    private InsertIncomeInvoiceService insertIncomeInvoiceService;
    private InvoiceRepository invoiceRepository;


    @BeforeEach
    void setUp() {
        invoiceRepository = new InMemoryInvoiceRepo();
        insertIncomeInvoiceService = new InsertIncomeInvoiceService(invoiceRepository);
    }

    @Test
    void checkIfIncomeInvoicesAreAddedToUserCorrectly() {
        //given
        Customer customer2 = DataCreator.createCustomer2();
        UUID customerId = customer2.getCustomerId();
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1().withCustomer(customer2);
        IncomeInvoice incomeInvoice2 = DataCreator.createIncomeInvoice2().withCustomer(customer2);
        IncomeInvoice incomeInvoice3 = DataCreator.createIncomeInvoice3().withCustomer(customer2);
        IncomeInvoice incomeInvoice4 = DataCreator.createIncomeInvoice4().withCustomer(customer2);
        IncomeInvoice incomeInvoice5 = DataCreator.createIncomeInvoice5().withCustomer(customer2);

        //when
        insertIncomeInvoiceService.insertIncomeInvoice(customerId, incomeInvoice1);
        insertIncomeInvoiceService.insertIncomeInvoice(customerId, incomeInvoice2);
        insertIncomeInvoiceService.insertIncomeInvoice(customerId, incomeInvoice3);
        insertIncomeInvoiceService.insertIncomeInvoice(customerId, incomeInvoice4);
        insertIncomeInvoiceService.insertIncomeInvoice(customerId, incomeInvoice5);

        //then
        Assertions.assertEquals(5, customer2.getIncomeInvoices().size());
        Assertions.assertEquals(5, invoiceRepository.listAllInvoicesForCustomerId(customer2.getCustomerId()).size());


    }
}
