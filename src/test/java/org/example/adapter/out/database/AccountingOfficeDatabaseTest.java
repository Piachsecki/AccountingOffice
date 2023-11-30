package org.example.adapter.out.database;


import lombok.extern.slf4j.Slf4j;
import org.example.Data;
import org.example.DataCreator;
import org.example.adapter.out.database.configuration.DatabaseHibernateConfig;
import org.example.adapter.out.database.repository.CustomerStorage;
import org.example.adapter.out.database.repository.InvoiceRepositoryImpl;
import org.example.application.InsertCostInvoiceService;
import org.example.application.InsertIncomeInvoiceService;
import org.example.application.InsertInvoiceService;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.domain.customer.Entrepreneurship;
import org.example.domain.customer.EntrepreneurshipForm;
import org.example.domain.customer.TaxPayments.IndustryType;
import org.example.domain.customer.TaxPayments.LumpSumTax;
import org.example.domain.customer.TaxPayments.TaxPaymentForm;
import org.example.domain.invoice.IncomeInvoice;
import org.example.port.out.CustomerRepository;
import org.example.port.out.InvoiceRepository;
import org.junit.jupiter.api.*;

import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class AccountingOfficeDatabaseTest {
    private CustomerRepository customerRepository;
    private InvoiceRepository invoiceRepository;
    private InsertInvoiceService insertInvoiceService;
    private

    @BeforeEach
    void initClasses(){
        customerRepository = new CustomerStorage();
        invoiceRepository = new InvoiceRepositoryImpl();
        insertInvoiceService = new InsertInvoiceService(
                new InsertCostInvoiceService(invoiceRepository),
                new InsertIncomeInvoiceService(invoiceRepository)
        );

    }

    @AfterAll
    static void afterAll(){
        DatabaseHibernateConfig.closeFactory();
    }

    @Order(1)
    @DisplayName("Test responsible for deleting all of the data from the database")
    @Test
    void clean(){
        log.info("### RUNNING ORDER 1");
        customerRepository.deleteAllCustomers();
//        invoiceRepository.deleteAllInvoicesForCustomerId();
    }

    @Order(2)
    @DisplayName("Test responsible for adding customers and companies successfully to the database")
    @Test()
    void init(){
        log.info("### RUNNING ORDER 2");
        Customer customer1 = DataCreator.createCustomer1().withEntrepreneurshipForm(new Entrepreneurship(EntrepreneurshipForm.SOLE_PROPRIETORSHIP, new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)));
        customerRepository.addCustomer(customer1);

    }

    @Order(3)
    @Test
    void addInvoice(){
        log.info("### RUNNING ORDER 3");
    }



    @Test
    void deleteSingleUserFromDatabase(){
//        Customer customer1 = DataCreator.createCustomer1();
//        System.out.println("before" + customer1.getCustomerId());
//        Customer customer = customerRepository.addCustomer(customer1);
//        System.out.println("after " + customer.getCustomerId());
//        customerRepository.deleteCustomer(new CustomerId("becf42f8-bb22-4932-8aee-f10090d2b679"));


    }


    @Test
    void findUserByNip(){
//        Customer customer = customerRepository.addCustomer(
//                DataCreator
//                        .createCustomer2()
//                        .withEntrepreneurshipForm(new Entrepreneurship(
//                                EntrepreneurshipForm.SOLE_PROPRIETORSHIP, new LumpSumTax(IndustryType.TENANT)))
//        );
        Optional<Customer> customerByNIP = customerRepository.findCustomerByNIP(new NIP("9527816928"));
        System.out.println(customerByNIP);


    }




}
