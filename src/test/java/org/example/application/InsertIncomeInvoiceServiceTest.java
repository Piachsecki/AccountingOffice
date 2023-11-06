package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.InMemoryInvoiceRepo;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.IncomeInvoice;
import org.example.port.out.InvoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InsertIncomeInvoiceServiceTest {
    @InjectMocks
    private InsertIncomeInvoiceService insertIncomeInvoiceService = new InsertIncomeInvoiceService();
    @Mock
    private InvoiceRepository invoiceRepository = new InMemoryInvoiceRepo();



    @Test
    void checkIfIncomeInvoicesAreAddedToUserCorrectly(){
        //given
        Customer customer2 = DataCreator.createCustomer2();
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1().withCustomer(customer2);
        IncomeInvoice incomeInvoice2 = DataCreator.createIncomeInvoice2().withCustomer(customer2);
        IncomeInvoice incomeInvoice3 = DataCreator.createIncomeInvoice3().withCustomer(customer2);
        IncomeInvoice incomeInvoice4 = DataCreator.createIncomeInvoice4().withCustomer(customer2);
        IncomeInvoice incomeInvoice5 = DataCreator.createIncomeInvoice5().withCustomer(customer2);

        //when
        insertIncomeInvoiceService.insertIncomeInvoice(incomeInvoice1);
        insertIncomeInvoiceService.insertIncomeInvoice(incomeInvoice2);
        insertIncomeInvoiceService.insertIncomeInvoice(incomeInvoice3);
        insertIncomeInvoiceService.insertIncomeInvoice(incomeInvoice4);
        insertIncomeInvoiceService.insertIncomeInvoice(incomeInvoice5);

        //then
        Assertions.assertEquals(5, customer2.getIncomeInvoices().size());


    }
}
