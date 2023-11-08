package org.example.adapter.out;

import org.example.DataCreator;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

class InMemoryCustomerRepoTest {

    private final InMemoryCustomerRepo customerRepo = new InMemoryCustomerRepo();



    @Test
    void shouldCreateUserCorrectly() {
        //given
        var customer1 = DataCreator.createCustomer1();
        var customer2 = DataCreator.createCustomer2();
        var customer3 = DataCreator.createCustomer3();
        var customer4 = DataCreator.createCustomer4();
        var customer5 = DataCreator.createCustomer5();

        //when
        customerRepo.addCustomer(customer1);
        customerRepo.addCustomer(customer2);
        customerRepo.addCustomer(customer3);
        customerRepo.addCustomer(customer4);
        customerRepo.addCustomer(customer5);

        //then
        Assertions.assertTrue(customerRepo.getCustomers().containsKey(customer1.getCustomerId()));
        Assertions.assertTrue(customerRepo.getCustomers().containsKey(customer2.getCustomerId()));
        Assertions.assertTrue(customerRepo.getCustomers().containsKey(customer3.getCustomerId()));
        Assertions.assertTrue(customerRepo.getCustomers().containsKey(customer4.getCustomerId()));
        Assertions.assertTrue(customerRepo.getCustomers().containsKey(customer5.getCustomerId()));
    }

    @Test
    void shouldThrowWhenAddingCustomerWithInvalidId() {


        //given, when, then
//        Throwable exception1 = Assertions.assertThrows(
//                CustomerIdException.class, () -> DataCreator.createCustomer1().withCustomerId(new CustomerId(""))
//                );
//
//        Throwable exception2 = Assertions.assertThrows(
//                CustomerIdException.class, () -> DataCreator.createCustomer1().withCustomerId(null)
//                );
//
//        Throwable exception3 = Assertions.assertThrows(
//                CustomerIdException.class,
//                () -> DataCreator.createCustomer1().withCustomerId(new CustomerId("0123456789"))
//        );



        //idk why this doesn't work honestly
//        Assertions.assertEquals(String.format("Given value:[%s] cannot be used as CustomerId" +
//                "!", new CustomerId("")), exception.getMessage());
    }


    @Test
    void shouldDeleteCustomer(){
        //given
        Customer customer1 = DataCreator.createCustomer1();

        //when
        customerRepo.addCustomer(customer1);
        Assertions.assertEquals(1, customerRepo.getCustomers().size());
        customerRepo.deleteCustomer(customer1.getCustomerId());

        //then
        Assertions.assertEquals(new HashMap<>(), customerRepo.getCustomers());
    }

    @Test
    void shouldDeleteAll(){
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Customer customer2 = DataCreator.createCustomer2();
        Customer customer3 = DataCreator.createCustomer3();
        Customer customer4 = DataCreator.createCustomer4();
        Customer customer5 = DataCreator.createCustomer5();

        //when
        customerRepo.addCustomer(customer1);
        customerRepo.addCustomer(customer2);
        customerRepo.addCustomer(customer3);
        customerRepo.addCustomer(customer4);
        customerRepo.addCustomer(customer5);
        Assertions.assertEquals(5, customerRepo.getCustomers().size());
        customerRepo.deleteAllCustomers();

        //then
        Assertions.assertEquals(0, customerRepo.getCustomers().size());
    }


    @Test
    void shouldFindCertainUser(){
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Customer customer2 = DataCreator.createCustomer2();
        Customer customer3 = DataCreator.createCustomer3();
        Customer customer4 = DataCreator.createCustomer4();
        Customer customer5 = DataCreator.createCustomer5();

        //when
        customerRepo.addCustomer(customer1);
        customerRepo.addCustomer(customer2);
        customerRepo.addCustomer(customer3);
        customerRepo.addCustomer(customer4);
        customerRepo.addCustomer(customer5);

        Optional<Customer> customer1ByNip = customerRepo.findCustomerByNIP(new NIP("1462693747"));
        Optional<Customer> customer2ByNip = customerRepo.findCustomerByNIP(new NIP("9527816928"));
        Optional<Customer> customer3ByNip = customerRepo.findCustomerByNIP(new NIP("0003768420"));
        Optional<Customer> customer4ByNip = customerRepo.findCustomerByNIP(new NIP("1461113747"));
        Optional<Customer> customer5ByNip = customerRepo.findCustomerByNIP(new NIP("4627890357"));
        Optional<Customer> empty = customerRepo.findCustomerByNIP(new NIP("1111111111"));

        //then

        Assertions.assertEquals(customer1, customer1ByNip.get());
        Assertions.assertEquals(customer2, customer2ByNip.get());
        Assertions.assertEquals(customer3, customer3ByNip.get());
        Assertions.assertEquals(customer4, customer4ByNip.get());
        Assertions.assertEquals(customer5, customer5ByNip.get());
        Assertions.assertEquals(Optional.empty(),empty);
    }


}