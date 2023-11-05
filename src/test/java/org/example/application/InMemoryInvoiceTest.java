package org.example.application;

import org.example.adapter.out.InMemoryInvoiceRepo;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.Invoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

class InMemoryInvoiceTest {
    private final InMemoryInvoiceRepo inMemoryInvoiceRepo = new InMemoryInvoiceRepo();

    @Test
    void shouldCreateInvoicesCorrectly() {
        //given
        Invoice invoice1 = DataCreator.createCostInvoice1();
        Invoice invoice2 = DataCreator.createCostInvoice2();
        Invoice invoice3 = DataCreator.createCostInvoice3();
        Invoice invoice4 = DataCreator.createCostInvoice4();
        Invoice invoice5 = DataCreator.createCostInvoice5();

        //when
        inMemoryInvoiceRepo.insertCostInvoice(invoice1);
        inMemoryInvoiceRepo.insertCostInvoice(invoice2);
        inMemoryInvoiceRepo.insertCostInvoice(invoice3);
        inMemoryInvoiceRepo.insertCostInvoice(invoice4);
        inMemoryInvoiceRepo.insertCostInvoice(invoice5);

        //then
        Assertions.assertTrue(inMemoryInvoiceRepo.getInvoices().containsKey(invoice1.getCustomer().getCustomerId()));
        Assertions.assertTrue(inMemoryInvoiceRepo.getInvoices().containsKey(invoice2.getCustomer().getCustomerId()));
        Assertions.assertTrue(inMemoryInvoiceRepo.getInvoices().containsKey(invoice3.getCustomer().getCustomerId()));
        Assertions.assertTrue(inMemoryInvoiceRepo.getInvoices().containsKey(invoice4.getCustomer().getCustomerId()));
        Assertions.assertTrue(inMemoryInvoiceRepo.getInvoices().containsKey(invoice5.getCustomer().getCustomerId()));

    }


    @Test
    void shouldAddInvoiceToAlreadyExistingCustomer() {
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Invoice invoice1 = DataCreator.createCostInvoice1();
        Invoice invoice2 = DataCreator.createCostInvoice2().withCustomer(customer1);

        //when
        inMemoryInvoiceRepo.insertCostInvoice(invoice1);
        inMemoryInvoiceRepo.insertCostInvoice(invoice2);

        //then
        Assertions.assertEquals(2, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
    }


    @Test
    void shouldListAllOfTheInvoicesForCustomer() {
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Invoice invoice1 = DataCreator.createCostInvoice1().withCustomer(customer1);
        Invoice invoice2 = DataCreator.createCostInvoice2().withCustomer(customer1);
        Invoice invoice3 = DataCreator.createCostInvoice3().withCustomer(customer1);
        Invoice invoice4 = DataCreator.createCostInvoice4().withCustomer(customer1);
        Invoice invoice5 = DataCreator.createCostInvoice5().withCustomer(customer1);

        //when
        inMemoryInvoiceRepo.insertCostInvoice(invoice1);
        inMemoryInvoiceRepo.insertCostInvoice(invoice2);
        inMemoryInvoiceRepo.insertCostInvoice(invoice3);
        inMemoryInvoiceRepo.insertCostInvoice(invoice4);
        inMemoryInvoiceRepo.insertCostInvoice(invoice5);

        //then
        System.out.println(inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()));
        Assertions.assertEquals(5,
                inMemoryInvoiceRepo.listAllInvoicesForCustomerId(customer1.getCustomerId()).size());
    }

    @Test
    void deleteInvoice() {
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Invoice invoice1 = DataCreator.createCostInvoice1().withCustomer(customer1);
        Invoice invoice2 = DataCreator.createCostInvoice2().withCustomer(customer1);
        Invoice invoice3 = DataCreator.createCostInvoice3().withCustomer(customer1);

        //when
        inMemoryInvoiceRepo.insertCostInvoice(invoice1);
        inMemoryInvoiceRepo.insertCostInvoice(invoice2);
        inMemoryInvoiceRepo.insertCostInvoice(invoice3);
        Assertions.assertEquals(3, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
        inMemoryInvoiceRepo.deleteInvoiceForCustomerId(customer1.getCustomerId(), invoice2.getInvoiceId());

        //then
        Assertions.assertEquals(2, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
    }

    @Test
    void deleteAllInvoices(){
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Invoice invoice1 = DataCreator.createCostInvoice1();
        Invoice invoice2 = DataCreator.createCostInvoice2().withCustomer(customer1);
        Invoice invoice3 = DataCreator.createCostInvoice3().withCustomer(customer1);

        //when
        inMemoryInvoiceRepo.insertCostInvoice(invoice1);
        inMemoryInvoiceRepo.insertCostInvoice(invoice2);
        inMemoryInvoiceRepo.insertCostInvoice(invoice3);
        Assertions.assertEquals(3, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
        inMemoryInvoiceRepo.deleteAllInvoicesForCustomerId(customer1.getCustomerId());

        //then
        Assertions.assertEquals(new HashSet<>(), inMemoryInvoiceRepo.listAllInvoicesForCustomerId(customer1.getCustomerId()));
    }


}