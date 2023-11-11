package org.example.adapter.out;

import org.example.DataCreator;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.money.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashSet;

class InMemoryInvoiceRepoTest {

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
        inMemoryInvoiceRepo.insertInvoice(invoice1);
        inMemoryInvoiceRepo.insertInvoice(invoice2);
        inMemoryInvoiceRepo.insertInvoice(invoice3);
        inMemoryInvoiceRepo.insertInvoice(invoice4);
        inMemoryInvoiceRepo.insertInvoice(invoice5);

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
        inMemoryInvoiceRepo.insertInvoice(invoice1);
        inMemoryInvoiceRepo.insertInvoice(invoice2);
        inMemoryInvoiceRepo.insertInvoice(invoice3);
        inMemoryInvoiceRepo.insertInvoice(invoice4);
        inMemoryInvoiceRepo.insertInvoice(invoice5);

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
        inMemoryInvoiceRepo.insertInvoice(invoice1);
        inMemoryInvoiceRepo.insertInvoice(invoice2);
        inMemoryInvoiceRepo.insertInvoice(invoice3);
        Assertions.assertEquals(3, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
        inMemoryInvoiceRepo.deleteInvoiceForCustomerId(customer1.getCustomerId(), invoice2.getInvoiceId());

        //then
        Assertions.assertEquals(2, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
    }

    @Test
    void deleteAllInvoices(){
        //given
        Customer customer1 = DataCreator.createCustomer1();
        Invoice invoice1 = DataCreator.createCostInvoice1().withCustomer(customer1);
        Invoice invoice2 = DataCreator.createCostInvoice2().withCustomer(customer1);
        Invoice invoice3 = DataCreator.createCostInvoice3().withCustomer(customer1);

        //when
        inMemoryInvoiceRepo.insertInvoice(invoice1);
        inMemoryInvoiceRepo.insertInvoice(invoice2);
        inMemoryInvoiceRepo.insertInvoice(invoice3);
        Assertions.assertEquals(3, inMemoryInvoiceRepo.getInvoices().get(customer1.getCustomerId()).size());
        inMemoryInvoiceRepo.deleteAllInvoicesForCustomerId(customer1.getCustomerId());

        //then
        Assertions.assertEquals(new HashSet<>(), inMemoryInvoiceRepo.listAllInvoicesForCustomerId(customer1.getCustomerId()));
    }

    @Test
    void shouldCountRevenueCorrectly(){
        //given
        Customer customer1 = DataCreator.createCustomer1();
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1().withCustomer(customer1);
        IncomeInvoice incomeInvoice2 = DataCreator.createIncomeInvoice2().withCustomer(customer1);
        IncomeInvoice incomeInvoice3 = DataCreator.createIncomeInvoice3().withCustomer(customer1);
        IncomeInvoice incomeInvoice4 = DataCreator.createIncomeInvoice4().withCustomer(customer1);
        IncomeInvoice incomeInvoice5 = DataCreator.createIncomeInvoice5().withCustomer(customer1);
        CostInvoice costInvoice1 = DataCreator.createCostInvoice1();

        //when
        inMemoryInvoiceRepo.insertInvoice(incomeInvoice1);
        inMemoryInvoiceRepo.insertInvoice(incomeInvoice2);
        inMemoryInvoiceRepo.insertInvoice(incomeInvoice3);
        inMemoryInvoiceRepo.insertInvoice(incomeInvoice4);
        inMemoryInvoiceRepo.insertInvoice(incomeInvoice5);
        inMemoryInvoiceRepo.insertInvoice(costInvoice1);
        Money result = inMemoryInvoiceRepo.countMonthlyRevenueUseCase(customer1.getCustomerId(), YearMonth.of(2025, 10));

        BigDecimal expected = incomeInvoice1.getAmount().countToPLN()
                .add(incomeInvoice2.getAmount().countToPLN())
                .add(incomeInvoice3.getAmount().countToPLN())
                .add(incomeInvoice4.getAmount().countToPLN())
                .add(incomeInvoice5.getAmount().countToPLN());

        System.out.println("expected: " + expected);
        System.out.println("result: " + result);


        //then

        Assertions.assertEquals(expected, result.amount());
    }

    @Test
    void shouldCountCostsCorrectly(){
        //given
        Customer customer1 = DataCreator.createCustomer1();
        IncomeInvoice incomeInvoice1 = DataCreator.createIncomeInvoice1().withCustomer(customer1);
        IncomeInvoice incomeInvoice2 = DataCreator.createIncomeInvoice2().withCustomer(customer1);
        IncomeInvoice incomeInvoice3 = DataCreator.createIncomeInvoice3().withCustomer(customer1);
        IncomeInvoice incomeInvoice4 = DataCreator.createIncomeInvoice4().withCustomer(customer1);
        IncomeInvoice incomeInvoice5 = DataCreator.createIncomeInvoice5().withCustomer(customer1);
        CostInvoice costInvoice1 = DataCreator.createCostInvoice1().withCustomer(customer1);

        //when
        inMemoryInvoiceRepo.insertInvoice(incomeInvoice1);
        inMemoryInvoiceRepo.insertInvoice(incomeInvoice2);
        inMemoryInvoiceRepo.insertInvoice(incomeInvoice3);
        inMemoryInvoiceRepo.insertInvoice(incomeInvoice4);
        inMemoryInvoiceRepo.insertInvoice(incomeInvoice5);
        inMemoryInvoiceRepo.insertInvoice(costInvoice1);
        Money result = inMemoryInvoiceRepo.countMonthlyCosts(customer1.getCustomerId(), YearMonth.of(2025, 10));

        BigDecimal expected = costInvoice1.getAmount().countToPLN();

        System.out.println("expected: " + expected);
        System.out.println("result: " + result);


        //then

        Assertions.assertEquals(expected, result.amount());
    }


}