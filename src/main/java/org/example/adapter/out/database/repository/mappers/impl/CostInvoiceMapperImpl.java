package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.entity.CostInvoiceDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.CostInvoiceMapper;
import org.example.domain.invoice.CostInvoice;

public class CostInvoiceMapperImpl implements CostInvoiceMapper {
    @Override
    public CostInvoice costInvoiceEntityToCostInvoiceDomainMapper(CostInvoiceDatabaseEntity costInvoiceDatabaseEntity) {
        return null;
    }

    @Override
    public CostInvoiceDatabaseEntity costInvoiceDomainToCostInvoiceDatabaseEntityMapper(CostInvoice costInvoiceDomain) {
        return null;
    }
}
