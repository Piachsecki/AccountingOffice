package org.example.adapter.out.database.repository.mappers;

import org.example.adapter.out.database.entity.CompanyDatabaseEntity;
import org.example.domain.company.Company;

public interface CompanyMapper {
    Company companyEntityToCompanyDomainMapper(CompanyDatabaseEntity companyDatabaseEntity);

    CompanyDatabaseEntity companyDomainToCompanyDatabaseEntityMapper(Company companyDomain);
}
