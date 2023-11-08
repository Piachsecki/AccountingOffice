package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.InvoiceId;
import org.example.port.in.invoice.DeleteInvoiceUseCase;
import org.example.port.out.InvoiceRepository;

@AllArgsConstructor
public class DeleteInvoiceService implements DeleteInvoiceUseCase {
    private InvoiceRepository invoiceRepository;
    @Override
    public void deleteInvoice(CustomerId customerId, InvoiceId invoiceId) {
        invoiceRepository.deleteInvoiceForCustomerId(customerId, invoiceId);
    }

    @Override
    public void deleteAll(CustomerId customerId) {
        invoiceRepository.deleteAllInvoicesForCustomerId(customerId);
    }

    @Override
    public void deleteAllWithUser(CustomerId customerId) {
        invoiceRepository.deleteAllWithCustomer(customerId);
    }
}
