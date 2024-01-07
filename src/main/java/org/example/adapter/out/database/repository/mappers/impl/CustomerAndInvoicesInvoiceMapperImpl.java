package org.example.adapter.out.database.repository.mappers.impl;

import lombok.AllArgsConstructor;
import org.example.adapter.out.database.entity.CostInvoiceDatabaseEntity;
import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.example.adapter.out.database.entity.IncomeInvoiceDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.*;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerAndInvoicesInvoiceMapperImpl implements CustomerInvoiceMapper {
    private AddressMapper addressMapper;
    private EntrepreneurshipMapper entrepreneurshipMapper;
    private ProductMapper productMapper;
    private PriceMapper priceMapper;
    private MoneyMapper moneyMapper;
    private CompanyMapper companyMapper;

    @Override
    public Customer customerEntityToCustomerDomainWithoutInvoicesMapper(CustomerDatabaseEntity customerDatabaseEntity) {
        return Customer.builder()
                .customerId(customerDatabaseEntity.getCustomerId())
                .name(customerDatabaseEntity.getName())
                .surname(customerDatabaseEntity.getSurname())
                .nip(new NIP(customerDatabaseEntity.getNip()))
                .address(addressMapper.addressEntityToAddressDomainMapper(customerDatabaseEntity.getAddress()))
                .joinDate(customerDatabaseEntity.getJoinDate())
                .entrepreneurshipForm(
                        entrepreneurshipMapper.mapToDomainEntrepreneurshipFromCustomerDatabaseEntity(customerDatabaseEntity))
                .build();
    }

    @Override
    public CustomerDatabaseEntity customerDomainToCustomerDatabaseEntityMapper(Customer customerDomain) {
        return CustomerDatabaseEntity
                .builder()
                .name(customerDomain.getName())
                .surname(customerDomain.getSurname())
                .joinDate(customerDomain.getJoinDate())
                .entrepreneurshipForm(customerDomain.getEntrepreneurshipForm().entrepreneurshipForm().toString())
                .nip(customerDomain.getNip().value())
                .taxPaymentForm(customerDomain.getEntrepreneurshipForm().taxPaymentForm().toString())
                .taxRate(customerDomain.getEntrepreneurshipForm().taxPaymentForm().getTaxRate().getValue())
                .address(addressMapper.addressDomainToAddressDatabaseEntityMapper(customerDomain.getAddress()))
                .build();
    }

    @Override
    public CostInvoice costInvoiceEntityToCostInvoiceDomainMapper(CostInvoiceDatabaseEntity costInvoiceDatabaseEntity) {
        return new CostInvoice(
                costInvoiceDatabaseEntity.getInvoiceId(),
                customerEntityToCustomerDomainWithoutInvoicesMapper(costInvoiceDatabaseEntity.getCustomer()),
                costInvoiceDatabaseEntity.getDate(),
                priceMapper.mapToDomainPriceFromDatabase(costInvoiceDatabaseEntity.getCurrency(), costInvoiceDatabaseEntity.getAmount()),
                companyMapper.companyEntityToCompanyDomainMapper(costInvoiceDatabaseEntity.getCompany()),
                productMapper.productEntityToProductDomainMapper(costInvoiceDatabaseEntity.getProduct())
        );
    }


    @Override
    public CostInvoiceDatabaseEntity costInvoiceDomainToCostInvoiceDatabaseEntityMapper(CostInvoice costInvoiceDomain) {
        return CostInvoiceDatabaseEntity.builder()
                .date(costInvoiceDomain.getDate())
                .currency(costInvoiceDomain.getAmount().currency().toString())
                .amount(costInvoiceDomain.getAmount().amount())
                .company(companyMapper.companyDomainToCompanyDatabaseEntityMapper(costInvoiceDomain.getCompany()))
                .product(productMapper.productDomainToProductDatabaseEntityMapper(costInvoiceDomain.getProduct()))
                .customer(customerDomainToCustomerDatabaseEntityMapper(costInvoiceDomain.getCustomer()))
                .build();
    }

    @Override
    public IncomeInvoiceDatabaseEntity incomeInvoiceDomainToIncomeInvoiceDatabaseEntityMapper(IncomeInvoice incomeInvoiceDomain) {
        return IncomeInvoiceDatabaseEntity.builder()
                .date(incomeInvoiceDomain.getDate())
                .amount(incomeInvoiceDomain.getAmount().amount())
                .currency(incomeInvoiceDomain.getAmount().currency().toString())
                .customer(customerDomainToCustomerDatabaseEntityMapper(incomeInvoiceDomain.getCustomer()))
                .build();
    }


    @Override
    public IncomeInvoice incomeInvoiceEntityToIncomeInvoiceDomainMapper(IncomeInvoiceDatabaseEntity incomeInvoiceDatabaseEntity) {
        return new IncomeInvoice(
                incomeInvoiceDatabaseEntity.getInvoiceId(),
                customerEntityToCustomerDomainWithoutInvoicesMapper(
                        incomeInvoiceDatabaseEntity.getCustomer()
                ),
                incomeInvoiceDatabaseEntity.getDate(),
                moneyMapper.mapToDomainMoneyFromDatabase(
                        incomeInvoiceDatabaseEntity.getCurrency(),
                        incomeInvoiceDatabaseEntity.getAmount()
                )
        );
    }

    @Override
    public Customer customerEntityToCustomerDomainMapper(CustomerDatabaseEntity customerDatabaseEntity) {
        Customer customer = customerEntityToCustomerDomainWithoutInvoicesMapper(customerDatabaseEntity);
        customer.setIncomeInvoices(
                customerDatabaseEntity.getIncomeInvoices()
                        .stream()
                        .map(this::incomeInvoiceEntityToIncomeInvoiceDomainMapper)
                        .toList());
        customer.setCostInvoices(
                customerDatabaseEntity.getCostInvoices()
                        .stream()
                        .map(this::costInvoiceEntityToCostInvoiceDomainMapper)
                        .toList());
        return customer;
    }


}
