package org.example.port.out;

import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;

import java.util.HashSet;

public interface InvoiceRepository {
    void sendInvoice(Invoice invoice);

    HashSet<Invoice> listAllInvoicesForCustomerId(CustomerId customerId);

    void deleteInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId);

    void deleteAllInvoicesForCustomerId(CustomerId customerId);
}
