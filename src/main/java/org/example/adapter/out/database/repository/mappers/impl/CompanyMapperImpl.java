package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.entity.CompanyDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.CompanyMapper;
import org.example.domain.company.Company;

public class CompanyMapperImpl implements CompanyMapper {
    @Override
    public Company companyEntityToCompanyDomainMapper(CompanyDatabaseEntity companyDatabaseEntity) {
        return null;
    }

    @Override
    public CompanyDatabaseEntity companyDomainToCompanyDatabaseEntityMapper(Company companyDomain) {
        return null;
    }
}
