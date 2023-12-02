package org.example.adapter.out.database.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.adapter.out.database.configuration.DatabaseHibernateConfig;
import org.example.adapter.out.database.entity.*;
import org.example.domain.Address;
import org.example.domain.company.Company;
import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.product.Product;
import org.example.port.out.InvoiceRepository;
import org.hibernate.Session;

import java.util.*;

import static org.example.adapter.out.database.repository.EntityToDomainClassMapper.*;

@Slf4j
public class InvoiceDatabaseStorage implements InvoiceRepository {
    @Override
    public Invoice insertInvoice(CustomerId customerId, Invoice invoice) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                log.error("Session is null");
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();
            String query = "SELECT cust FROM CustomerDatabaseEntity cust WHERE cust.customerId = :customerId";
            Optional<CustomerDatabaseEntity> customerToAddInvoices = session.createQuery(query, CustomerDatabaseEntity.class)
                    .setParameter("customerId", customerId.getCustomerIdAsUUID())
                    .uniqueResultOptional();

            if (!customerToAddInvoices.isPresent()) {
                log.error("Couldn't find customer from database");
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
                session.getTransaction().commit();
                return ((CostInvoice) invoice).withInvoiceId(new InvoiceId(costInvoiceDatabaseEntity.getInvoiceId().toString()));


            } else if (invoice instanceof IncomeInvoice) {
                IncomeInvoiceDatabaseEntity incomeInvoiceDatabaseEntity = IncomeInvoiceDatabaseEntity.builder()
                        .date(invoice.getDate())
                        .amount(((IncomeInvoice) invoice).getAmount().amount())
                        .currency(((IncomeInvoice) invoice).getAmount().currency().toString())
                        .customer(customerToAddInvoices.get())
                        .build();
                session.persist(incomeInvoiceDatabaseEntity);
                session.getTransaction().commit();
                return ((IncomeInvoice) invoice).withInvoiceId(new InvoiceId(incomeInvoiceDatabaseEntity.getInvoiceId().toString()));
            }

        }
        log.error("Cannot return mapped IncomeInvoice/CostInvoice from database correctly");
        throw new RuntimeException("Cannot return mapped IncomeInvoice/CostInvoice from database correctly");
    }

    @Override
    public HashSet<Invoice> listAllInvoicesForCustomerId(CustomerId customerId) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                log.error("Session is null");
                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();

            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public void deleteCostInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                log.error("Session is null");

                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();
            session.remove(session.find(CostInvoiceDatabaseEntity.class, invoiceId.getCustomerIdAsUUID()));
            session.getTransaction().commit();

        }
    }

    @Override
    public void deleteIncomeInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                log.error("Session is null");

                throw new RuntimeException("Session is null");
            }
            session.beginTransaction();
            session.remove(session.find(IncomeInvoiceDatabaseEntity.class, invoiceId.getCustomerIdAsUUID()));
            session.getTransaction().commit();

        }
    }


    @Override
    public List<Invoice> listCostInvoices(CustomerId customerId) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                log.error("Session is null");

                throw new RuntimeException("Session is null");
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
                log.error("Session is null");

                throw new RuntimeException("Session is null");
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


}
