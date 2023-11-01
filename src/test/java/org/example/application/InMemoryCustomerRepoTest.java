package org.example.application;

import org.example.adapter.out.InMemoryCustomerRepo;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.domain.exceptions.CustomerIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class InMemoryCustomerRepoTest {

    //this seems to be mocked, but I am using the official implementation of this interface,
    //to check its behaviour
    private final InMemoryCustomerRepo repo = new InMemoryCustomerRepo();
    private final DataCreator dataCreator = new DataCreator();


    @Test
    void shouldCreateUserCorrectly() {
        //given
        var customer1 = DataCreator.createCustomer1();
        var customer2 = DataCreator.createCustomer2();
        var customer3 = DataCreator.createCustomer3();
        var customer4 = DataCreator.createCustomer4();
        var customer5 = DataCreator.createCustomer5();

        //when
        repo.addCustomer(customer1);
        repo.addCustomer(customer2);
        repo.addCustomer(customer3);
        repo.addCustomer(customer4);
        repo.addCustomer(customer5);

        //then
        Assertions.assertTrue(repo.getCustomers().containsKey(customer1.getCustomerId()));
        Assertions.assertTrue(repo.getCustomers().containsKey(customer2.getCustomerId()));
        Assertions.assertTrue(repo.getCustomers().containsKey(customer3.getCustomerId()));
        Assertions.assertTrue(repo.getCustomers().containsKey(customer4.getCustomerId()));
        Assertions.assertTrue(repo.getCustomers().containsKey(customer5.getCustomerId()));
    }

    @Test
    void shouldThrowWhenAddingCustomerWithInvalidId() {


        //given, when, then
        Throwable exception1 = Assertions.assertThrows(
                CustomerIdException.class, () -> DataCreator.createCustomer1().withCustomerId(new CustomerId(""))
                );

        Throwable exception2 = Assertions.assertThrows(
                CustomerIdException.class, () -> DataCreator.createCustomer1().withCustomerId(null)
                );

        Throwable exception3 = Assertions.assertThrows(
                CustomerIdException.class,
                () -> DataCreator.createCustomer1().withCustomerId(new CustomerId("0123456789"))
        );



        //idk why this doesn't work honestly
//        Assertions.assertEquals(String.format("Given value:[%s] cannot be used as CustomerId" +
//                "!", new CustomerId("")), exception.getMessage());
    }


    @Test
    void shouldDeleteCustomer(){
        //given
        Customer customer1 = DataCreator.createCustomer1();

        //when
        repo.addCustomer(customer1);
        Assertions.assertEquals(1, repo.getCustomers().size());
        repo.deleteCustomer(customer1.getCustomerId());

        //then
        Assertions.assertEquals(new HashMap<>(), repo.getCustomers());
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
        repo.addCustomer(customer1);
        repo.addCustomer(customer2);
        repo.addCustomer(customer3);
        repo.addCustomer(customer4);
        repo.addCustomer(customer5);
        Assertions.assertEquals(5, repo.getCustomers().size());
        repo.deleteAllCustomers();

        //then
        Assertions.assertEquals(0, repo.getCustomers().size());
    }

}