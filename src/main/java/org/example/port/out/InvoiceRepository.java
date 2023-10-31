package org.example.port.out;

import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;

import java.util.HashSet;

public interface InvoiceRepository {
    void createInvoice(Invoice invoice);

    HashSet<Invoice> listAllInvoices(CustomerId customerId);

    void deleteInvoice(CustomerId customerId, InvoiceId invoiceId);

    void deleteAll(CustomerId customerId);
}
