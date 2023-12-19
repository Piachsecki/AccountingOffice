package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.entity.IncomeInvoiceDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.IncomeInvoiceMapper;
import org.example.domain.invoice.IncomeInvoice;

public class IncomeInvoiceMapperImpl implements IncomeInvoiceMapper {
    @Override
    public IncomeInvoice incomeInvoiceEntityToIncomeInvoiceDomainMapper(IncomeInvoiceDatabaseEntity incomeInvoiceDatabaseEntity) {
        return null;
    }

    @Override
    public IncomeInvoiceDatabaseEntity incomeInvoiceDomainToIncomeInvoiceDatabaseEntityMapper(IncomeInvoice incomeInvoiceDomain) {
        return null;
    }
}
