package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.port.in.invoice.InsertInvoiceUseCase;
import org.example.port.in.invoice.ListInvoiceUseCase;
import org.example.port.out.InvoiceRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class InvoiceService implements InsertInvoiceUseCase, ListInvoiceUseCase {
    private InvoiceRepository invoiceRepository;
    private InsertCostInvoiceService insertCostInvoiceService;
    private InsertIncomeInvoiceService insertIncomeInvoiceService;


    @Override
    public <T extends Invoice> Invoice insertInvoice(UUID customerId, T invoice) {
        if (IncomeInvoice.class.equals(invoice.getClass())) {
            return insertIncomeInvoiceService.insertIncomeInvoice(customerId, (IncomeInvoice) invoice);
        } else {
            return insertCostInvoiceService.insertCostInvoice(customerId, (CostInvoice) invoice);
        }

    }

    @Override
    public Set<Invoice> listAllInvoicesForCustomerId(UUID customerId) {
        return invoiceRepository.listAllInvoicesForCustomerId(customerId);
    }

    @Override
    public List<Invoice> listCostInvoices(UUID customerId) {
        return invoiceRepository.listCostInvoices(customerId);
    }

    @Override
    public List<Invoice> listIncomeInvoices(UUID customerId) {
        return invoiceRepository.listIncomeInvoices(customerId);
    }
}
