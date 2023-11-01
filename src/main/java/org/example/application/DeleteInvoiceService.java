package org.example.application;

import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.InvoiceId;
import org.example.port.in.invoice.DeleteInvoiceUseCase;
import org.example.port.out.InvoiceRepository;

public class DeleteInvoiceService implements DeleteInvoiceUseCase {
    private InvoiceRepository invoiceRepository;
    @Override
    public void deleteInvoice(CustomerId customerId, InvoiceId invoiceId) {
        invoiceRepository.deleteInvoice(customerId, invoiceId);
    }

    @Override
    public void deleteAll(CustomerId customerId) {
        invoiceRepository.deleteAll(customerId);
    }
}
