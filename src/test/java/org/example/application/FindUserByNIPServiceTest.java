package org.example.application;

import org.example.DataCreator;
import org.example.adapter.out.maps.InMemoryCustomerRepo;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.port.out.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class FindUserByNIPServiceTest {
    private CustomerService customerService;
    private CustomerRepository customerRepository;


    @BeforeEach
    void setUp() {
        customerRepository = new InMemoryCustomerRepo();
        customerService = new CustomerService(customerRepository);
    }


    @Test
    void shouldReturnOptionalEmpty() {
        //given, when, then
        Assertions.assertEquals(Optional.empty(), customerRepository.findCustomerByNIP(DataCreator.createCustomer1().getNip()));
    }


    @Test
    void shouldFindACustomer() {
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Customer customer2 = DataCreator.createCustomer2();
        Customer customer3 = DataCreator.createCustomer3();
        Customer customer4 = DataCreator.createCustomer4();


        //when
        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);
        customerService.addCustomer(customer3);
        customerService.addCustomer(customer4);

        Customer customerByNip1 = customerService.findUserByNIP(customer1.getNip());
        Customer customerByNip2 =customerService.findUserByNIP(customer2.getNip());
        Customer customerByNip3 =customerService.findUserByNIP(customer3.getNip());
        Customer customerByNip4 =customerService.findUserByNIP(customer4.getNip());

        //then
        Assertions.assertNotNull(customerByNip1);
        Assertions.assertNotNull(customerByNip2);
        Assertions.assertNotNull(customerByNip3);
        Assertions.assertNotNull(customerByNip4);
    }

}
