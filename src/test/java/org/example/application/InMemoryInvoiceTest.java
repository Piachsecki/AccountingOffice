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
        Invoice invoice1 = DataCreator.createInvoice1();
        Invoice invoice2 = DataCreator.createInvoice2();
        Invoice invoice3 = DataCreator.createInvoice3();
        Invoice invoice4 = DataCreator.createInvoice4();
        Invoice invoice5 = DataCreator.createInvoice5();

        //when
        inMemoryInvoiceRepo.sendInvoice(invoice1);
        inMemoryInvoiceRepo.sendInvoice(invoice2);
        inMemoryInvoiceRepo.sendInvoice(invoice3);
        inMemoryInvoiceRepo.sendInvoice(invoice4);
        inMemoryInvoiceRepo.sendInvoice(invoice5);

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
        Invoice invoice1 = DataCreator.createInvoice1();
        Invoice invoice2 = DataCreator.createInvoice2().withCustomer(customer1);

        //when
        inMemoryInvoiceRepo.sendInvoice(invoice1);
        inMemoryInvoiceRepo.sendInvoice(invoice2);

        //then
        Assertions.assertEquals(2, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
    }


    @Test
    void shouldListAllOfTheInvoicesForCustomer() {
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Invoice invoice1 = DataCreator.createInvoice1().withCustomer(customer1);
        Invoice invoice2 = DataCreator.createInvoice2().withCustomer(customer1);
        Invoice invoice3 = DataCreator.createInvoice3().withCustomer(customer1);
        Invoice invoice4 = DataCreator.createInvoice4().withCustomer(customer1);
        Invoice invoice5 = DataCreator.createInvoice5().withCustomer(customer1);

        //when
        inMemoryInvoiceRepo.sendInvoice(invoice1);
        inMemoryInvoiceRepo.sendInvoice(invoice2);
        inMemoryInvoiceRepo.sendInvoice(invoice3);
        inMemoryInvoiceRepo.sendInvoice(invoice4);
        inMemoryInvoiceRepo.sendInvoice(invoice5);

        //then
        Assertions.assertEquals(5,
                inMemoryInvoiceRepo.listAllInvoicesForCustomerId(customer1.getCustomerId()).size());
    }

    @Test
    void deleteInvoice() {
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Invoice invoice1 = DataCreator.createInvoice1().withCustomer(customer1);
        Invoice invoice2 = DataCreator.createInvoice2().withCustomer(customer1);
        Invoice invoice3 = DataCreator.createInvoice3().withCustomer(customer1);

        //when
        inMemoryInvoiceRepo.sendInvoice(invoice1);
        inMemoryInvoiceRepo.sendInvoice(invoice2);
        inMemoryInvoiceRepo.sendInvoice(invoice3);
        Assertions.assertEquals(3, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
        inMemoryInvoiceRepo.deleteInvoiceForCustomerId(customer1.getCustomerId(), invoice2.getInvoiceId());

        //then
        Assertions.assertEquals(2, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
    }

    @Test
    void deleteAllInvoices(){
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Invoice invoice1 = DataCreator.createInvoice1();
        Invoice invoice2 = DataCreator.createInvoice2().withCustomer(customer1);
        Invoice invoice3 = DataCreator.createInvoice3().withCustomer(customer1);

        //when
        inMemoryInvoiceRepo.sendInvoice(invoice1);
        inMemoryInvoiceRepo.sendInvoice(invoice2);
        inMemoryInvoiceRepo.sendInvoice(invoice3);
        Assertions.assertEquals(3, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
        inMemoryInvoiceRepo.deleteAllInvoicesForCustomerId(customer1.getCustomerId());

        //then
        Assertions.assertEquals(new HashSet<>(), inMemoryInvoiceRepo.listAllInvoicesForCustomerId(customer1.getCustomerId()));
    }


}