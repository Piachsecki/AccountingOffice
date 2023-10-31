package org.example.application;

import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.InvoiceId;
import org.example.port.in.invoice.DeleteInvoiceUseCase;
import org.example.port.out.InvoiceRepository;

public class DeleteInvoiceService implements DeleteInvoiceUseCase {
    private InvoiceRepository invoiceRepository;
    @Override
    public void deleteInvoice(InvoiceId invoiceId) {
        invoiceRepository.deleteInvoice(invoiceId);
    }

    @Override
    public void deleteAll(CustomerId customerId) {
        invoiceRepository.deleteAll(customerId);
    }
}
