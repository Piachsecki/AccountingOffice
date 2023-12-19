package org.example.adapter.out.database.repository.mappers;

import org.example.adapter.out.database.entity.IncomeInvoiceDatabaseEntity;
import org.example.domain.invoice.IncomeInvoice;

public interface IncomeInvoiceMapper {
    IncomeInvoice incomeInvoiceEntityToIncomeInvoiceDomainMapper(IncomeInvoiceDatabaseEntity incomeInvoiceDatabaseEntity);

    IncomeInvoiceDatabaseEntity incomeInvoiceDomainToIncomeInvoiceDatabaseEntityMapper(IncomeInvoice incomeInvoiceDomain);
}
