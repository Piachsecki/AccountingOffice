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

import java.util.Optional;

public class DeleteCustomerServiceTest {

    private DeleteCustomerService deleteCustomerService;
    private CustomerRepository customerRepository;
    private InvoiceRepository invoiceRepository;


    @BeforeEach
    void setUp(){
        invoiceRepository = new InMemoryInvoiceRepo();
        customerRepository = new InMemoryCustomerRepo();
        deleteCustomerService = new DeleteCustomerService(customerRepository);
    }
    @Test
    void testIfBothCustomersAndInvoicesReposAreDeletingData(){
        //given
        Customer customer1 = DataCreator.createCustomer1();
        CostInvoice costInvoice1 = DataCreator.createCostInvoice1().withCustomer(customer1);
        CostInvoice costInvoice2 = DataCreator.createCostInvoice2().withCustomer(customer1);
        CostInvoice costInvoice3 = DataCreator.createCostInvoice3().withCustomer(customer1);
        CostInvoice costInvoice4 = DataCreator.createCostInvoice4().withCustomer(customer1);
        CostInvoice costInvoice5 = DataCreator.createCostInvoice5().withCustomer(customer1);


        //when
        invoiceRepository.insertInvoice(customer1.getCustomerId(), costInvoice1);
        invoiceRepository.insertInvoice(customer1.getCustomerId(), costInvoice2);
        invoiceRepository.insertInvoice(customer1.getCustomerId(), costInvoice3);
        invoiceRepository.insertInvoice(customer1.getCustomerId(), costInvoice4);
        invoiceRepository.insertInvoice(customer1.getCustomerId(), costInvoice5);

        deleteCustomerService.deleteCustomer(customer1.getCustomerId());

        //then

        Assertions.assertEquals(Optional.empty(), customerRepository.findCustomerByNIP(customer1.getNip()));
        Assertions.assertNull(invoiceRepository.listAllInvoicesForCustomerId(customer1.getCustomerId()));

    }
}
