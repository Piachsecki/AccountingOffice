package org.example.port.in.invoice;

import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.InvoiceId;

public interface DeleteInvoiceUseCase {
    void deleteInvoice(InvoiceId invoiceId);

    void deleteAll(CustomerId customerId);
}
