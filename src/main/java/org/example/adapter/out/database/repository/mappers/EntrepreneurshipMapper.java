package org.example.adapter.out.database.repository.mappers;

import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.example.domain.customer.Entrepreneurship;

public interface EntrepreneurshipMapper {
    Entrepreneurship mapToDomainEntrepreneurshipFromCustomerDatabaseEntity(CustomerDatabaseEntity customerDatabaseEntity);
}
