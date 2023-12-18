package org.example.adapter.out.database.repository;

import org.example.adapter.out.database.entity.*;
import org.example.domain.Address;
import org.example.domain.NIP;
import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.customer.Entrepreneurship;
import org.example.domain.customer.EntrepreneurshipForm;
import org.example.domain.customer.TaxPayments.*;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.money.Money;
import org.example.domain.money.Price;
import org.example.domain.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EntityToDomainClassMapper {

    public static Money mapToMoneyFromDatabase(String currency, BigDecimal amount) {
        return new Money(amount, org.example.domain.money.Currency.valueOf(currency));
    }

    public static Company mapToCompanyFromCompanyDatabaseEntity(CompanyDatabaseEntity company) {
        return new Company(
                company.getCompanyName(),
                new NIP(company.getNip()),
                mapAddressFromAddressDatabaseEntity(company.getAddress())
        );
    }

    public static Customer mapToCustomerFromCustomerDatabaseEntity(CustomerDatabaseEntity customer) {
        return new Customer(
                customer.getCustomerId(),
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

    public static Address mapAddressFromAddressDatabaseEntity(AddressDatabaseEntity address) {
        return new Address(
                address.getCity(),
                address.getCountry(),
                address.getAddress(),
                address.getPostalCode()
        );
    }

    public static Price mapToPriceFromDatabase(String currency, BigDecimal amount) {
        return new Price(
                amount,
                org.example.domain.money.Currency.valueOf(currency));
    }

    public static Product mapToProductFromProductDatabaseEntity(ProductDatabaseEntity product) {
        return new Product(
                product.getProductId(),
                product.getProductName(),
                new Price(product.getAmount(), org.example.domain.money.Currency.valueOf(product.getCurrency()))
        );
    }


    public static Entrepreneurship mapEntrepreneurshipFromCustomerDatabaseEntity(CustomerDatabaseEntity customerDatabaseEntity) {
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


    public static Customer createCustomerFromCustomerDatabaseEntity(CustomerDatabaseEntity customerDatabaseEntity) {
        Customer customer = createCustomerWithoutInvoicesFromCustomerDatabaseEntity(customerDatabaseEntity);
        customer.setCostInvoices(mapCostInvoiceEntityToCostInvoiceDomain(customerDatabaseEntity.getCostInvoices()));
        customer.setIncomeInvoices(mapIncomeInvoiceEntityToIncomeInvoiceDomain(customerDatabaseEntity.getIncomeInvoices()));
        return customer;
    }

    public static Customer createCustomerWithoutInvoicesFromCustomerDatabaseEntity(CustomerDatabaseEntity customerDatabaseEntity) {
        return Customer.builder()
                .customerId(customerDatabaseEntity.getCustomerId())
                .name(customerDatabaseEntity.getName())
                .surname(customerDatabaseEntity.getSurname())
                .nip(new NIP(customerDatabaseEntity.getNip()))
                .address(mapAddressFromAddressDatabaseEntity(customerDatabaseEntity.getAddress()))
                .joinDate(customerDatabaseEntity.getJoinDate())
                .entrepreneurshipForm(mapEntrepreneurshipFromCustomerDatabaseEntity(customerDatabaseEntity))
                .build();
    }

    private static List<IncomeInvoice> mapIncomeInvoiceEntityToIncomeInvoiceDomain(Set<IncomeInvoiceDatabaseEntity> incomeDatabaseInvoices) {
        List<IncomeInvoice> incomeDomainInvoices = new ArrayList<>();
        for (IncomeInvoiceDatabaseEntity incomeInvoice : incomeDatabaseInvoices) {
            incomeDomainInvoices.add(mapIncomeDatabaseInvoice(incomeInvoice));
        }
        return incomeDomainInvoices;
    }

    private static IncomeInvoice mapIncomeDatabaseInvoice(IncomeInvoiceDatabaseEntity incomeInvoice) {
        return new IncomeInvoice(
                incomeInvoice.getInvoiceId(),
                createCustomerWithoutInvoicesFromCustomerDatabaseEntity(incomeInvoice.getCustomer()),
                incomeInvoice.getDate(),
                mapToMoneyFromDatabase(incomeInvoice.getCurrency(), incomeInvoice.getAmount())
        );
    }

    private static List<CostInvoice> mapCostInvoiceEntityToCostInvoiceDomain(Set<CostInvoiceDatabaseEntity> costDatabaseInvoices) {
        List<CostInvoice> costInvoicesDomain = new ArrayList<>();
        for (CostInvoiceDatabaseEntity costInvoice : costDatabaseInvoices) {
            costInvoicesDomain.add(mapCostDatabaseInvoice(costInvoice));
        }
        return costInvoicesDomain;
    }


    private static CostInvoice mapCostDatabaseInvoice(CostInvoiceDatabaseEntity costInvoiceDatabaseEntity) {
        return new CostInvoice(
                costInvoiceDatabaseEntity.getInvoiceId(),
                createCustomerWithoutInvoicesFromCustomerDatabaseEntity(costInvoiceDatabaseEntity.getCustomer()),
                costInvoiceDatabaseEntity.getDate(),
                mapToPriceFromDatabase(costInvoiceDatabaseEntity.getCurrency(), costInvoiceDatabaseEntity.getAmount()),
                mapToCompanyFromCompanyDatabaseEntity(costInvoiceDatabaseEntity.getCompany()),
                mapToProductFromProductDatabaseEntity(costInvoiceDatabaseEntity.getProduct())
        );
    }


}
