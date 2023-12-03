package org.example.port.in.invoice;


import java.util.UUID;

public interface DeleteInvoiceUseCase {
    void deleteInvoice(UUID customerId, UUID invoiceId);

}
