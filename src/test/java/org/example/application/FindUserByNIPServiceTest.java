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
    private FindUserByNIPService findUserByNIPService;
    private CustomerRepository customerRepository;


    @BeforeEach
    void setUp() {
        customerRepository = new InMemoryCustomerRepo();
        findUserByNIPService = new FindUserByNIPService(customerRepository);
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

        customerRepository.addCustomer(customer1);
        customerRepository.addCustomer(customer2);
        customerRepository.addCustomer(customer3);
        customerRepository.addCustomer(customer4);

        NIP nip1 = customerRepository.findCustomerByNIP(customer1.getNip()).get().getNip();
        NIP nip2 = customerRepository.findCustomerByNIP(customer2.getNip()).get().getNip();
        NIP nip3 = customerRepository.findCustomerByNIP(customer3.getNip()).get().getNip();
        NIP nip4 = customerRepository.findCustomerByNIP(customer4.getNip()).get().getNip();

        //then
        Assertions.assertEquals(customer1.getNip() ,nip1);
        Assertions.assertEquals(customer2.getNip() ,nip2);
        Assertions.assertEquals(customer3.getNip() ,nip3);
        Assertions.assertEquals(customer4.getNip() ,nip4);
    }

}
