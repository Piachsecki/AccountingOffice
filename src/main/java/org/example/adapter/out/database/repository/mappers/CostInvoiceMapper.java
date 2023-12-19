package org.example.adapter.out.database.repository.mappers;

import org.example.adapter.out.database.entity.CostInvoiceDatabaseEntity;
import org.example.domain.invoice.CostInvoice;

public interface CostInvoiceMapper {
    CostInvoice costInvoiceEntityToCostInvoiceDomainMapper(CostInvoiceDatabaseEntity costInvoiceDatabaseEntity);

    CostInvoiceDatabaseEntity costInvoiceDomainToCostInvoiceDatabaseEntityMapper(CostInvoice costInvoiceDomain);
}
