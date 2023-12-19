package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.entity.AddressDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.AddressMapper;
import org.example.domain.Address;

public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address addressEntityToAddressDomainMapper(AddressDatabaseEntity addressDatabaseEntity) {
        return null;
    }

    @Override
    public AddressDatabaseEntity addressDomainToAddressDatabaseEntityMapper(Address addressDomain) {
        return null;
    }
}
