package org.example.port.out;

import org.example.domain.invoice.Invoice;

public interface InvoiceRepository {
    void createInvoice(Invoice invoice);
}
