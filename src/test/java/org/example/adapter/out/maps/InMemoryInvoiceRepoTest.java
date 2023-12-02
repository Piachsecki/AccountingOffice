package org.example.adapter.out.maps;

import org.example.DataCreator;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.Invoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InMemoryInvoiceRepoTest {

    private final InMemoryInvoiceRepo inMemoryInvoiceRepo = new InMemoryInvoiceRepo();


    @Test
    void shouldCreateInvoicesCorrectly() {
        //given
        Customer customer = DataCreator.createCustomer1();
        Invoice invoice1 = DataCreator.createCostInvoice1();
        Invoice invoice2 = DataCreator.createCostInvoice2();
        Invoice invoice3 = DataCreator.createCostInvoice3();
        Invoice invoice4 = DataCreator.createCostInvoice4();
        Invoice invoice5 = DataCreator.createCostInvoice5();

        //when
        inMemoryInvoiceRepo.insertInvoice(customer.getCustomerId(), invoice1);
        inMemoryInvoiceRepo.insertInvoice(customer.getCustomerId(), invoice2);
        inMemoryInvoiceRepo.insertInvoice(customer.getCustomerId(), invoice3);
        inMemoryInvoiceRepo.insertInvoice(customer.getCustomerId(), invoice4);
        inMemoryInvoiceRepo.insertInvoice(customer.getCustomerId(), invoice5);

        //then
        Assertions.assertTrue(inMemoryInvoiceRepo.getInvoices().containsKey(invoice1.getCustomer().getCustomerId()));
        Assertions.assertTrue(inMemoryInvoiceRepo.getInvoices().containsKey(invoice2.getCustomer().getCustomerId()));
        Assertions.assertTrue(inMemoryInvoiceRepo.getInvoices().containsKey(invoice3.getCustomer().getCustomerId()));
        Assertions.assertTrue(inMemoryInvoiceRepo.getInvoices().containsKey(invoice4.getCustomer().getCustomerId()));
        Assertions.assertTrue(inMemoryInvoiceRepo.getInvoices().containsKey(invoice5.getCustomer().getCustomerId()));

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
        inMemoryInvoiceRepo.insertInvoice(customer1.getCustomerId(), invoice1);
        inMemoryInvoiceRepo.insertInvoice(customer1.getCustomerId(), invoice2);
        inMemoryInvoiceRepo.insertInvoice(customer1.getCustomerId(), invoice3);
        inMemoryInvoiceRepo.insertInvoice(customer1.getCustomerId(), invoice4);
        inMemoryInvoiceRepo.insertInvoice(customer1.getCustomerId(), invoice5);

        //then
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
        inMemoryInvoiceRepo.insertInvoice(customer1.getCustomerId(), invoice1);
        inMemoryInvoiceRepo.insertInvoice(customer1.getCustomerId(), invoice2);
        inMemoryInvoiceRepo.insertInvoice(customer1.getCustomerId(), invoice3);
        Assertions.assertEquals(3, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
        inMemoryInvoiceRepo.deleteCostInvoiceForCustomerId(customer1.getCustomerId(), invoice2.getInvoiceId());

        //then
        Assertions.assertEquals(2, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
    }
}