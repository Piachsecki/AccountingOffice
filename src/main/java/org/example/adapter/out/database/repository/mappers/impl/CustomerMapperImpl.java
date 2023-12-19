package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.CustomerMapper;
import org.example.domain.customer.Customer;

public class CustomerMapperImpl implements CustomerMapper {
    @Override
    public Customer customerEntityToCustomerDomainMapper(CustomerDatabaseEntity customerDatabaseEntity) {
        return null;
    }

    @Override
    public CustomerDatabaseEntity customerDomainToCustomerDatabaseEntityMapper(Customer customerDomain) {
        return null;
    }
}
