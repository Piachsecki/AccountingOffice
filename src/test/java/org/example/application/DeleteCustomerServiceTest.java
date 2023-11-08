package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.InMemoryCustomerRepo;
import org.example.adapter.out.InMemoryInvoiceRepo;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.port.out.CustomerRepository;
import org.example.port.out.InvoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Optional;

public class DeleteCustomerServiceTest {

    private DeleteCustomerService deleteCustomerService;
    private CustomerRepository customerRepository;
    private DeleteInvoiceService deleteInvoiceService;
    private InvoiceRepository invoiceRepository;


    @BeforeEach
    void setUp(){
        invoiceRepository = new InMemoryInvoiceRepo();
        customerRepository = new InMemoryCustomerRepo();
        deleteInvoiceService = new DeleteInvoiceService(invoiceRepository);
        deleteCustomerService = new DeleteCustomerService(customerRepository, deleteInvoiceService);
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
        invoiceRepository.insertCostInvoice(costInvoice1);
        invoiceRepository.insertCostInvoice(costInvoice2);
        invoiceRepository.insertCostInvoice(costInvoice3);
        invoiceRepository.insertCostInvoice(costInvoice4);
        invoiceRepository.insertCostInvoice(costInvoice5);

        deleteCustomerService.deleteCustomer(customer1.getCustomerId());

        //then

        Assertions.assertEquals(Optional.empty(), customerRepository.findCustomerByNIP(customer1.getNip()));

        //TODO should this return null or Empty -> we dont have a customer anymore in our database
        Assertions.assertEquals(null, invoiceRepository.listAllInvoicesForCustomerId(customer1.getCustomerId()));

    }
}
