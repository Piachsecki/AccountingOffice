package org.example.adapter.out.database.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.adapter.out.database.entity.*;
import org.example.adapter.out.database.repository.mappers.CustomerInvoiceMapper;
import org.example.domain.Address;
import org.example.domain.company.Company;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.product.Product;
import org.example.port.out.InvoiceRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


@AllArgsConstructor
@Slf4j
@Repository
public class InvoiceDatabaseStorage implements InvoiceRepository {
    private CustomerInvoiceMapper customerInvoiceMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Invoice insertInvoice(UUID customerId, Invoice invoice) {
        String query = "SELECT cust FROM CustomerDatabaseEntity cust WHERE cust.customerId = :customerId";
        Optional<CustomerDatabaseEntity> customerToAddInvoices = Optional.of(entityManager.createQuery(query, CustomerDatabaseEntity.class)
                .setParameter("customerId", customerId)
                .getSingleResult());

        if (customerToAddInvoices.isEmpty()) {
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

//            CostInvoiceDatabaseEntity costInvoiceDatabaseEntity = customerInvoiceMapper.costInvoiceDomainToCostInvoiceDatabaseEntityMapper((CostInvoice) invoice);

                CostInvoiceDatabaseEntity costInvoiceDatabaseEntity = CostInvoiceDatabaseEntity.builder()
                        .date(invoice.getDate())
                        .currency(((CostInvoice) invoice).getAmount().currency().toString())
                        .amount(((CostInvoice) invoice).getAmount().amount())
                        .company(companyDatabaseEntity)
                        .product(productDatabaseEntity)
                        .customer(customerToAddInvoices.get())
                        .build();
                costInvoiceDatabaseEntity.setCustomer(customerToAddInvoices.get());
            entityManager.persist(costInvoiceDatabaseEntity);
            return ((CostInvoice) invoice).withInvoiceId(costInvoiceDatabaseEntity.getInvoiceId());


        } else if (invoice instanceof IncomeInvoice) {
                IncomeInvoiceDatabaseEntity incomeInvoiceDatabaseEntity = IncomeInvoiceDatabaseEntity.builder()
                        .date(invoice.getDate())
                        .amount(((IncomeInvoice) invoice).getAmount().amount())
                        .currency(((IncomeInvoice) invoice).getAmount().currency().toString())
                        .customer(customerToAddInvoices.get())
                        .build();
//            IncomeInvoiceDatabaseEntity incomeInvoiceDatabaseEntity = customerInvoiceMapper.incomeInvoiceDomainToIncomeInvoiceDatabaseEntityMapper((IncomeInvoice) invoice);
            entityManager.persist(incomeInvoiceDatabaseEntity);
            return ((IncomeInvoice) invoice).withInvoiceId(incomeInvoiceDatabaseEntity.getInvoiceId());
        }


        log.error("Cannot return mapped IncomeInvoice/CostInvoice from database correctly");
        throw new RuntimeException("Cannot return mapped IncomeInvoice/CostInvoice from database correctly");
    }

    @Override
    public HashSet<Invoice> listAllInvoicesForCustomerId(UUID customerId) {
        if (Objects.isNull(entityManager)) {
            log.error("Session is null");
            throw new RuntimeException("Session is null");
        }
        return null;

    }

    @Override
    public void deleteCostInvoiceForCustomerId(UUID customerId, UUID invoiceId) {
        if (Objects.isNull(entityManager)) {
            log.error("Session is null");

            throw new RuntimeException("Session is null");
        }
        entityManager.remove(entityManager.find(CostInvoiceDatabaseEntity.class, invoiceId));
    }

    @Override
    public void deleteIncomeInvoiceForCustomerId(UUID customerId, UUID invoiceId) {
        if (Objects.isNull(entityManager)) {
            log.error("Session is null");

            throw new RuntimeException("Session is null");
        }
        entityManager.remove(entityManager.find(IncomeInvoiceDatabaseEntity.class, invoiceId));


    }


    @Override
    public List<Invoice> listCostInvoices(UUID customerId) {
        if (Objects.isNull(entityManager)) {
            log.error("Session is null");

            throw new RuntimeException("Session is null");
        }
        String query = "SELECT costInv FROM CostInvoiceDatabaseEntity costInv where costInv.customer.customerId = :customerId";
        List<CostInvoiceDatabaseEntity> costInvoices = entityManager.createQuery(query, CostInvoiceDatabaseEntity.class)
                .setParameter("customerId", customerId)
                .getResultList();

        List<Invoice> result = new ArrayList<>();
        for (CostInvoiceDatabaseEntity costInvoiceDatabaseEntity : costInvoices) {
//                CostInvoice invoice = new CostInvoice(
//                        costInvoiceDatabaseEntity.getInvoiceId(),
//                        mapToCustomerFromCustomerDatabaseEntity(costInvoiceDatabaseEntity.getCustomer()),
//                        costInvoiceDatabaseEntity.getDate(),
//                        mapToPriceFromDatabase(costInvoiceDatabaseEntity.getCurrency(), costInvoiceDatabaseEntity.getAmount()),
//                        mapToCompanyFromCompanyDatabaseEntity(costInvoiceDatabaseEntity.getCompany()),
//                        mapToProductFromProductDatabaseEntity(costInvoiceDatabaseEntity.getProduct())
//                );
            CostInvoice costInvoice = customerInvoiceMapper.costInvoiceEntityToCostInvoiceDomainMapper(costInvoiceDatabaseEntity);

            result.add(costInvoice);
        }

        return result;

    }

    @Override
    public List<Invoice> listIncomeInvoices(UUID customerId) {
        if (Objects.isNull(entityManager)) {
            log.error("Session is null");

            throw new RuntimeException("Session is null");
        }
        String query = "SELECT incomeInv FROM IncomeInvoiceDatabaseEntity incomeInv where incomeInv.customer.customerId = :customerId";
        List<IncomeInvoiceDatabaseEntity> incomeInvoices = entityManager.createQuery(query, IncomeInvoiceDatabaseEntity.class)
                .setParameter("customerId", customerId)
                .getResultList();

        List<Invoice> result = new ArrayList<>();
        for (IncomeInvoiceDatabaseEntity incomeInvoiceDatabaseEntity : incomeInvoices) {
//                IncomeInvoice invoice = new IncomeInvoice(
//                        incomeInvoiceDatabaseEntity.getInvoiceId(),
//                        mapToCustomerFromCustomerDatabaseEntity(incomeInvoiceDatabaseEntity.getCustomer()),
//                        incomeInvoiceDatabaseEntity.getDate(),
//                        mapToMoneyFromDatabase(incomeInvoiceDatabaseEntity.getCurrency(), incomeInvoiceDatabaseEntity.getAmount())
//                );
            IncomeInvoice incomeInvoice = customerInvoiceMapper.incomeInvoiceEntityToIncomeInvoiceDomainMapper(incomeInvoiceDatabaseEntity);
            result.add(incomeInvoice);

        }

        return result;


    }


}
