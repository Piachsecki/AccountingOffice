package org.example.adapter.out.database.repository.mappers.impl;

import lombok.AllArgsConstructor;
import org.example.adapter.out.database.entity.CompanyDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.AddressMapper;
import org.example.adapter.out.database.repository.mappers.CompanyMapper;
import org.example.domain.NIP;
import org.example.domain.company.Company;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyMapperImpl implements CompanyMapper {
    private AddressMapper addressMapper;
    @Override
    public Company companyEntityToCompanyDomainMapper(CompanyDatabaseEntity companyDatabaseEntity) {
        return new Company(
                companyDatabaseEntity.getCompanyName(),
                new NIP(companyDatabaseEntity.getNip()),
                addressMapper.addressEntityToAddressDomainMapper(companyDatabaseEntity.getAddress())
        );
    }

    @Override
    public CompanyDatabaseEntity companyDomainToCompanyDatabaseEntityMapper(Company companyDomain) {
        return CompanyDatabaseEntity.builder()
                .companyName(companyDomain.getCompanyName())
                .nip(companyDomain.getNip().toString())
                .address(addressMapper.addressDomainToAddressDatabaseEntityMapper(companyDomain.getAddress()))
                .build();
    }
}
