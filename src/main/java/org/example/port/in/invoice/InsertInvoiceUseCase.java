package org.example.port.in.invoice;

import org.example.domain.invoice.Invoice;

public interface InsertInvoiceUseCase {
    <T extends Invoice> Invoice insertInvoice(T invoice);
}
