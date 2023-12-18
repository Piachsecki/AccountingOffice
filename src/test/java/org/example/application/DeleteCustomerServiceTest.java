package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.maps.InMemoryCustomerRepo;
import org.example.adapter.out.maps.InMemoryInvoiceRepo;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.port.out.CustomerRepository;
import org.example.port.out.InvoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteCustomerServiceTest {

    private CustomerService customerService;
    private InvoiceService invoiceService;


    @BeforeEach
    void setUp() {
        InvoiceRepository invoiceRepository = new InMemoryInvoiceRepo();
        CustomerRepository customerRepository = new InMemoryCustomerRepo();
        customerService = new CustomerService(customerRepository);
        invoiceService = new InvoiceService(invoiceRepository, new InsertCostInvoiceService(invoiceRepository),
                new InsertIncomeInvoiceService(invoiceRepository));
    }

    @Test
    void testIfBothCustomersAndInvoicesReposAreDeletingData() {
        //given
        Customer customer1 = DataCreator.createCustomer1();
        CostInvoice costInvoice1 = DataCreator.createCostInvoice1().withCustomer(customer1);
        CostInvoice costInvoice2 = DataCreator.createCostInvoice2().withCustomer(customer1);
        CostInvoice costInvoice3 = DataCreator.createCostInvoice3().withCustomer(customer1);
        CostInvoice costInvoice4 = DataCreator.createCostInvoice4().withCustomer(customer1);
        CostInvoice costInvoice5 = DataCreator.createCostInvoice5().withCustomer(customer1);


        //when
        invoiceService.insertInvoice(customer1.getCustomerId(), costInvoice1);
        invoiceService.insertInvoice(customer1.getCustomerId(), costInvoice2);
        invoiceService.insertInvoice(customer1.getCustomerId(), costInvoice3);
        invoiceService.insertInvoice(customer1.getCustomerId(), costInvoice4);
        invoiceService.insertInvoice(customer1.getCustomerId(), costInvoice5);

        customerService.deleteCustomer(customer1.getCustomerId());

        //then
        Throwable exception = assertThrows(RuntimeException.class, () -> customerService.findUserByNIP(customer1.getNip()));
        Assertions.assertEquals(String.format("There is no user with the given NIP[%s]", customer1.getNip()), exception.getMessage());

    }
}
