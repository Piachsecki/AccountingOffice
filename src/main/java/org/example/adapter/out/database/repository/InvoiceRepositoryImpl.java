package org.example.adapter.out.database.repository;

import org.example.adapter.out.database.configuration.DatabaseHibernateConfig;
import org.example.adapter.out.database.entity.*;
import org.example.domain.Address;
import org.example.domain.NIP;
import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.domain.customer.Entrepreneurship;
import org.example.domain.customer.EntrepreneurshipForm;
import org.example.domain.customer.TaxPayments.*;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.money.Money;
import org.example.domain.money.Price;
import org.example.domain.product.Product;
import org.example.domain.product.ProductId;
import org.example.port.out.InvoiceRepository;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.*;

public class InvoiceRepositoryImpl implements InvoiceRepository {
    @Override
    public void insertInvoice(CustomerId customerId, Invoice invoice) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException();
            }
            session.beginTransaction();
            String query = "SELECT cust FROM CustomerDatabaseEntity cust WHERE cust.customerId = :customerId";
            Optional<CustomerDatabaseEntity> customerToAddInvoices = session.createQuery(query, CustomerDatabaseEntity.class)
                    .setParameter("customerId", customerId.getCustomerIdAsUUID())
                    .uniqueResultOptional();

            if (!customerToAddInvoices.isPresent()) {
                throw new RuntimeException("There is not such a customer");
            }

            if (invoice instanceof CostInvoice) {
                Company company = ((CostInvoice) invoice).getCompany();
                Product product = ((CostInvoice) invoice).getProduct();
                Address address = ((CostInvoice) invoice).getCompany().getAddress();


                AddressDatabaseEntity addressDatabaseEntity = AddressDatabaseEntity.builder()
                        .city(address.getCity())
                        .country(address.getCountry())
                        .address(address.getAddress())
                        .postalCode(address.getPostalCode())
                        .build();


                CompanyDatabaseEntity companyDatabaseEntity = CompanyDatabaseEntity.builder()
                        .companyName(company.getCompanyName())
                        .nip(company.getNip().toString())
                        .address(addressDatabaseEntity)
                        .build();

                ProductDatabaseEntity productDatabaseEntity = ProductDatabaseEntity.builder()
                        .productName(product.getProductName())
                        .amount(product.getPrice().amount())
                        .currency(product.getPrice().currency().toString())
                        .build();


                CostInvoiceDatabaseEntity costInvoiceDatabaseEntity = CostInvoiceDatabaseEntity.builder()
                        .date(invoice.getDate())
                        .currency(((CostInvoice) invoice).getAmount().currency().toString())
                        .amount(((CostInvoice) invoice).getAmount().amount())
                        .company(companyDatabaseEntity)
                        .product(productDatabaseEntity)
                        .customer(customerToAddInvoices.get())
                        .build();
                session.persist(costInvoiceDatabaseEntity);

            } else if (invoice instanceof IncomeInvoice) {
                IncomeInvoiceDatabaseEntity incomeInvoiceDatabaseEntity = IncomeInvoiceDatabaseEntity.builder()
                        .date(invoice.getDate())
                        .amount(((IncomeInvoice) invoice).getAmount().amount())
                        .currency(((IncomeInvoice) invoice).getAmount().currency().toString())
                        .customer(customerToAddInvoices.get())

                        .build();
                session.persist(incomeInvoiceDatabaseEntity);
            }

            session.getTransaction().commit();

        }
    }

    @Override
    public HashSet<Invoice> listAllInvoicesForCustomerId(CustomerId customerId) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException();
            }
            session.beginTransaction();
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public void deleteInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId) {

    }

    @Override
    public void deleteAllInvoicesForCustomerId(CustomerId customerId) {

    }

    @Override
    public void deleteAllWithCustomer(CustomerId customerId) {

    }

    @Override
    public List<Invoice> listCostInvoices(CustomerId customerId) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException();
            }
            session.beginTransaction();
            String query = "SELECT costInv FROM CostInvoiceDatabaseEntity costInv where costInv.customer.customerId = :customerId";
            List<CostInvoiceDatabaseEntity> costInvoices = session.createQuery(query, CostInvoiceDatabaseEntity.class)
                    .setParameter("customerId", customerId.getCustomerIdAsUUID())
                    .list();
            session.getTransaction().commit();

            List<Invoice> result = new ArrayList<>();
            for (CostInvoiceDatabaseEntity costInvoice : costInvoices) {
                CostInvoice invoice = new CostInvoice(
                        new InvoiceId(costInvoice.getInvoiceId().toString()),
                        mapToCustomerFromCustomerDatabaseEntity(costInvoice.getCustomer()),
                        costInvoice.getDate(),
                        mapToPriceFromDatabase(costInvoice.getCurrency(), costInvoice.getAmount()),
                        mapToCompanyFromCompanyDatabaseEntity(costInvoice.getCompany()),
                        mapToProductFromProductDatabaseEntity(costInvoice.getProduct())
                );

                result.add(invoice);
            }

            return result;
        }
    }

    @Override
    public List<Invoice> listIncomeInvoices(CustomerId customerId) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException();
            }
            session.beginTransaction();
            String query = "SELECT incomeInv FROM IncomeInvoiceDatabaseEntity incomeInv where incomeInv.customer.customerId = :customerId";
            List<IncomeInvoiceDatabaseEntity> incomeInvoices = session.createQuery(query, IncomeInvoiceDatabaseEntity.class)
                    .setParameter("customerId", customerId.getCustomerIdAsUUID())
                    .list();
            session.getTransaction().commit();

            List<Invoice> result = new ArrayList<>();
            for (IncomeInvoiceDatabaseEntity incomeInvoice : incomeInvoices) {
                IncomeInvoice invoice = new IncomeInvoice(
                        new InvoiceId(incomeInvoice.getInvoiceId().toString()),
                        mapToCustomerFromCustomerDatabaseEntity(incomeInvoice.getCustomer()),
                        incomeInvoice.getDate(),
                        mapToMoneyFromDatabase(incomeInvoice.getCurrency(), incomeInvoice.getAmount())
                );
                result.add(invoice);

            }

            return result;
        }

    }

    private Money mapToMoneyFromDatabase(String currency, BigDecimal amount) {
        return new Money(amount, org.example.domain.money.Currency.valueOf(currency));
    }

    private Company mapToCompanyFromCompanyDatabaseEntity(CompanyDatabaseEntity company) {
        return new Company(
                company.getCompanyName(),
                new NIP(company.getNip().toString()),
                mapAddressFromAddressDatabaseEntity(company.getAddress())
        );
    }

    private Customer mapToCustomerFromCustomerDatabaseEntity(CustomerDatabaseEntity customer) {
        return new Customer(
                new CustomerId(customer.getCustomerId().toString()),
                customer.getName(),
                customer.getSurname(),
                new NIP(customer.getNip()),
                mapAddressFromAddressDatabaseEntity(customer.getAddress()),
                customer.getJoinDate(),
                mapEntrepreneurshipFromCustomerDatabaseEntity(customer),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    private Address mapAddressFromAddressDatabaseEntity(AddressDatabaseEntity address) {
        return new Address(
                address.getCity(),
                address.getCountry(),
                address.getAddress(),
                address.getPostalCode()
        );
    }

    private Price mapToPriceFromDatabase(String currency, BigDecimal amount) {
        return new Price(
                amount,
                org.example.domain.money.Currency.valueOf(currency));
    }

    private Product mapToProductFromProductDatabaseEntity(ProductDatabaseEntity product) {
        return new Product(
                new ProductId(product.getProductId().toString()),
                product.getProductName(),
                new Price(product.getAmount(), org.example.domain.money.Currency.valueOf(product.getCurrency()))
        );
    }


    private Entrepreneurship mapEntrepreneurshipFromCustomerDatabaseEntity(CustomerDatabaseEntity customerDatabaseEntity) {
        EntrepreneurshipForm entrepreneurshipForm = EntrepreneurshipForm.valueOf(customerDatabaseEntity.getEntrepreneurshipForm());
        String taxPaymentForm = customerDatabaseEntity.getTaxPaymentForm();
        TaxRate taxRate = null;
        for (TaxRate value : TaxRate.values()) {
            if (customerDatabaseEntity.getTaxRate().equals(value.getValue())) {
                taxRate = value;
            }
        }

        TaxPaymentForm taxPaymentFormToReturn = null;
        IndustryType industryType = null;
        if ("LumpSumTax".equals(taxPaymentForm)) {
            if ("0.17".equals(taxRate.getValue())) {
                industryType = IndustryType.SOFTWARE_DEVELOPER;
            }
            if ("0.15".equals(taxRate.getValue())) {
                industryType = IndustryType.DOCTOR;
            }
            if ("0.085".equals(taxRate.getValue())) {
                industryType = IndustryType.TENANT;
            }
            if ("0.055".equals(taxRate.getValue())) {
                industryType = IndustryType.FARMER;
            }
            taxPaymentFormToReturn = new LumpSumTax(industryType);
        } else if ("FlatTax".equals(taxPaymentForm)) {
            taxPaymentFormToReturn = new FlatTax();
        } else {
            taxPaymentFormToReturn = new GeneralTax();

        }

        return new Entrepreneurship(entrepreneurshipForm, taxPaymentFormToReturn);
    }


}
