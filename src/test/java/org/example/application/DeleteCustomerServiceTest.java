package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.InMemoryCustomerRepo;
import org.example.domain.customer.Customer;
import org.example.port.out.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.xml.crypto.Data;

public class DeleteCustomerServiceTest {
    @InjectMocks
    private DeleteCustomerService deleteCustomerService = new DeleteCustomerService();
    @Mock
    private CustomerRepository customerRepository = new InMemoryCustomerRepo();
    @Mock
    private DeleteInvoiceService deleteInvoiceService = new DeleteInvoiceService();


    @Test
    void testIfBothCustomersAndInvoicesReposAreDeletingData(){
        Customer customer1 = DataCreator.createCustomer1();
        Customer customer2 = DataCreator.createCustomer2();
        Customer customer3 = DataCreator.createCustomer3();
        Customer customer4 = DataCreator.createCustomer4();
        Customer customer5 = DataCreator.createCustomer5();






    }
}
