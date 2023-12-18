package org.example.port.in.invoice;

import org.example.domain.invoice.Invoice;

import java.util.UUID;

public interface InsertInvoiceUseCase {
    <T extends Invoice> Invoice insertInvoice(UUID customerId, T invoice);
}
