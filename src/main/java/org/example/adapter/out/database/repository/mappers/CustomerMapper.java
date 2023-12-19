package org.example.adapter.out.database.repository.mappers;

import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.example.domain.customer.Customer;

public interface CustomerMapper {
    Customer customerEntityToCustomerDomainMapper(CustomerDatabaseEntity customerDatabaseEntity);

    CustomerDatabaseEntity customerDomainToCustomerDatabaseEntityMapper(Customer customerDomain);
}
