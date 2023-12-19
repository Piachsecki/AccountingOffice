package org.example.adapter.out.database;


import lombok.extern.slf4j.Slf4j;
import org.example.application.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class AccountingOfficeDatabaseTest {
    private InsertCostInvoiceService insertCostInvoiceService;
    private InsertIncomeInvoiceService insertIncomeInvoiceService;
    private InvoiceService invoiceService;
    private CustomerService customerService;
    private DeleteInvoiceService deleteInvoiceService;

//    @AfterAll
//    static void afterAll() {
//        DatabaseHibernateConfig.closeFactory();
//    }
//
//    private
//
//    @BeforeEach
//    void initClasses() {
//        CustomerRepository customerRepository = new CustomerDatabaseStorage();
//        InvoiceRepository invoiceRepository = new InvoiceDatabaseStorage();
//        insertCostInvoiceService = new InsertCostInvoiceService(invoiceRepository);
//        insertIncomeInvoiceService = new InsertIncomeInvoiceService(invoiceRepository);
//        customerService = new CustomerService(customerRepository);
//        invoiceService = new InvoiceService(invoiceRepository, insertCostInvoiceService, insertIncomeInvoiceService);
//        deleteInvoiceService = new DeleteInvoiceService(invoiceRepository);
//    }
//
//    @Order(1)
//    @DisplayName("Test responsible for deleting all of the data from the database")
//    @Test
//    void clean() {
//        log.info("### RUNNING ORDER 1");
//        customerService.deleteAll();
//    }
//
//    @Order(2)
//    @DisplayName("Test responsible for adding customers and companies successfully to the database")
//    @Test()
//    void init() {
//        log.info("### RUNNING ORDER 2");
//        Customer customer1 = DataCreator.createCustomer1().withEntrepreneurshipForm(new Entrepreneurship(EntrepreneurshipForm.SOLE_PROPRIETORSHIP, new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)));
//        customerService.addCustomer(customer1);
//    }
//
//    @Order(3)
//    @Test
//    void addCostInvoice() {
//        log.info("### RUNNING ORDER 3");
//        NIP nip = DataCreator.createCustomer1().getNip();
//        Customer userByNIP = customerService.findUserByNIP(nip);
//        CostInvoice costInvoice1 = DataCreator.createCostInvoice1().withCustomer(userByNIP);
//        invoiceService.insertInvoice(userByNIP.getCustomerId(), costInvoice1);
//    }
//
//    @Order(4)
//    @Test
//    void addIncomeInvoice() {
//        log.info("### RUNNING ORDER 4");
//        NIP nip = DataCreator.createCustomer1().getNip();
//        Customer userByNIP = customerService.findUserByNIP(nip);
//        IncomeInvoice incomeInvoice = DataCreator.createIncomeInvoice1().withCustomer(userByNIP);
//        invoiceService.insertInvoice(userByNIP.getCustomerId(), incomeInvoice);
//    }
//
//    @Order(5)
//    @Test
//    void deleteSingleUserFromDatabase() {
//        log.info("### RUNNING ORDER 5");
//        Customer customer = customerService.addCustomer(DataCreator.createCustomer2());
//        Customer customerFoundByNip = customerService.findUserByNIP(customer.getNip());
//        customerService.deleteCustomer(customerFoundByNip.getCustomerId());
//    }
//
//    @Order(6)
//    @Test
//    void findUserByNip() {
//        log.info("### RUNNING ORDER 6");
//
//        NIP nip = DataCreator.createCustomer1().getNip();
//        Customer userByNIP = customerService.findUserByNIP(nip);
//        Assertions.assertNotNull(userByNIP);
//    }
//    @Order(7)
//    @Test
//    void listIncomeInvoices() {
//        log.info("### RUNNING ORDER 7");
//
//        NIP nip = DataCreator.createCustomer1().getNip();
//        Customer userByNIP = customerService.findUserByNIP(nip);
//        IncomeInvoice incomeInvoice2 = DataCreator.createIncomeInvoice2().withCustomer(userByNIP);
//        IncomeInvoice incomeInvoice3 = DataCreator.createIncomeInvoice3().withCustomer(userByNIP);
//        IncomeInvoice incomeInvoice4 = DataCreator.createIncomeInvoice4().withCustomer(userByNIP);
//        IncomeInvoice incomeInvoice5 = DataCreator.createIncomeInvoice5().withCustomer(userByNIP);
//        invoiceService.insertInvoice(userByNIP.getCustomerId(), incomeInvoice2);
//        invoiceService.insertInvoice(userByNIP.getCustomerId(), incomeInvoice3);
//        invoiceService.insertInvoice(userByNIP.getCustomerId(), incomeInvoice4);
//        invoiceService.insertInvoice(userByNIP.getCustomerId(), incomeInvoice5);
//
//        List<Invoice> invoices = invoiceService.listIncomeInvoices(userByNIP.getCustomerId());
//        Assertions.assertEquals(5, invoices.size());
//    }
//    @Order(8)
//    @Test
//    void listCostInvoices() {
//        log.info("### RUNNING ORDER 8");
//
//        NIP nip = DataCreator.createCustomer1().getNip();
//        Customer userByNIP = customerService.findUserByNIP(nip);
//        CostInvoice costInvoice2 = DataCreator.createCostInvoice2().withCustomer(userByNIP);
//        CostInvoice costInvoice3 = DataCreator.createCostInvoice3().withCustomer(userByNIP);
//        CostInvoice costInvoice4 = DataCreator.createCostInvoice4().withCustomer(userByNIP);
//        CostInvoice costInvoice5 = DataCreator.createCostInvoice5().withCustomer(userByNIP);
//        invoiceService.insertInvoice(userByNIP.getCustomerId(), costInvoice2);
//        invoiceService.insertInvoice(userByNIP.getCustomerId(), costInvoice3);
//        invoiceService.insertInvoice(userByNIP.getCustomerId(), costInvoice4);
//        invoiceService.insertInvoice(userByNIP.getCustomerId(), costInvoice5);
//
//        List<Invoice> invoices = invoiceService.listCostInvoices(userByNIP.getCustomerId());
//        Assertions.assertEquals(5, invoices.size());
//    }

}
