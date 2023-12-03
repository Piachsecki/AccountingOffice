package org.example.adapter.out.database;


import lombok.extern.slf4j.Slf4j;
import org.example.DataCreator;
import org.example.adapter.out.database.configuration.DatabaseHibernateConfig;
import org.example.adapter.out.database.repository.CustomerDatabaseStorage;
import org.example.adapter.out.database.repository.InvoiceDatabaseStorage;
import org.example.application.InsertCostInvoiceService;
import org.example.application.InsertIncomeInvoiceService;
import org.example.application.InsertInvoiceService;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.domain.customer.Entrepreneurship;
import org.example.domain.customer.EntrepreneurshipForm;
import org.example.domain.customer.TaxPayments.IndustryType;
import org.example.domain.customer.TaxPayments.LumpSumTax;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.port.out.CustomerRepository;
import org.example.port.out.InvoiceRepository;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class AccountingOfficeDatabaseTest {
    private CustomerRepository customerRepository;
    private InvoiceRepository invoiceRepository;
    private InsertInvoiceService insertInvoiceService;
    private

    @BeforeEach
    void initClasses(){
        customerRepository = new CustomerDatabaseStorage();
        invoiceRepository = new InvoiceDatabaseStorage();
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

    }


    @Test
    void findUserByNip(){
        Optional<Customer> customerByNIP = customerRepository.findCustomerByNIP(new NIP("9527816928"));
        System.out.println(customerByNIP);


    }


    @Test
    void addIncomeInvoiceToCustomer(){
    }

    @Test
    void addCostInvoiceToCustomer(){

    }



    @Test
    void listCostInvoices(){
    }

    @Test
    void listIncomeInvoices(){

    }

    @Test
    void deleteIncomeInvoice(){
    }


    @Test
    void deleteCostInvoice(){
//        Customer customer1 = DataCreator.createCustomer1();
//        Customer customer = customerRepository.addCustomer(customer1);
//        CostInvoice costInvoice1 = DataCreator.createCostInvoice1();
//        CostInvoice costInvoice2 = DataCreator.createCostInvoice2();
//        Invoice invoice1 = invoiceRepository.insertInvoice(customer.getCustomerId(), costInvoice1);
//        Invoice invoice2 = invoiceRepository.insertInvoice(customer.getCustomerId(), costInvoice2);
//        invoiceRepository.deleteCostInvoiceForCustomerId(customer.getCustomerId(),invoice1.getInvoiceId());
    }


}
