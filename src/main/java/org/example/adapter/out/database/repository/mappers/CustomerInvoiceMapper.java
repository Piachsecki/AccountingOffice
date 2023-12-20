package org.example.adapter.out.database.repository.mappers;

import org.example.adapter.out.database.entity.CostInvoiceDatabaseEntity;
import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.example.adapter.out.database.entity.IncomeInvoiceDatabaseEntity;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;

public interface CustomerInvoiceMapper {
    Customer customerEntityToCustomerDomainWithoutInvoicesMapper(CustomerDatabaseEntity customerDatabaseEntity);

    CustomerDatabaseEntity customerDomainToCustomerDatabaseEntityMapper(Customer customerDomain);

    CostInvoice costInvoiceEntityToCostInvoiceDomainMapper(CostInvoiceDatabaseEntity costInvoiceDatabaseEntity);

    IncomeInvoice incomeInvoiceEntityToIncomeInvoiceDomainMapper(IncomeInvoiceDatabaseEntity incomeInvoiceDatabaseEntity);
    Customer customerEntityToCustomerDomainMapper(CustomerDatabaseEntity customerDatabaseEntity);

    CostInvoiceDatabaseEntity costInvoiceDomainToCostInvoiceDatabaseEntityMapper(CostInvoice costInvoiceDomain);
    IncomeInvoiceDatabaseEntity incomeInvoiceDomainToIncomeInvoiceDatabaseEntityMapper(IncomeInvoice incomeInvoiceDomain);



}
