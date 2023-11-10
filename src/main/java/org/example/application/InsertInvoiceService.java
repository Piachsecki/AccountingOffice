package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.port.in.invoice.InsertIncomeInvoiceUseCase;
import org.example.port.in.invoice.InsertInvoiceUseCase;

@AllArgsConstructor
public class InsertInvoiceService implements InsertInvoiceUseCase {
    private InsertCostInvoiceService insertCostInvoiceService;
    private InsertIncomeInvoiceService insertIncomeInvoiceService;

    @Override
    public <T extends Invoice> Invoice insertInvoice(T invoice) {
        if (IncomeInvoice.class.equals(invoice.getClass())) {
            return insertIncomeInvoiceService.insertIncomeInvoice((IncomeInvoice) invoice);
        }else {
            return insertCostInvoiceService.insertCostInvoice((CostInvoice) invoice);
        }

    }
}
