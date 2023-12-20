package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.entity.AddressDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.AddressMapper;
import org.example.domain.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address addressEntityToAddressDomainMapper(AddressDatabaseEntity addressDatabaseEntity) {
        return new Address(
                addressDatabaseEntity.getCity(),
                addressDatabaseEntity.getCountry(),
                addressDatabaseEntity.getAddress(),
                addressDatabaseEntity.getPostalCode()
        );
    }

    @Override
    public AddressDatabaseEntity addressDomainToAddressDatabaseEntityMapper(Address addressDomain) {
        return AddressDatabaseEntity
                .builder()
                .city(addressDomain.getCity())
                .country(addressDomain.getCountry())
                .address(addressDomain.getAddress())
                .postalCode(addressDomain.getPostalCode())
                .build();
    }
}
