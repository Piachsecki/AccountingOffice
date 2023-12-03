package org.example.port.out;

import org.example.domain.invoice.Invoice;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public interface InvoiceRepository {
    Invoice insertInvoice(UUID customerId, Invoice invoice);

    HashSet<Invoice> listAllInvoicesForCustomerId(UUID customerId);

    void deleteCostInvoiceForCustomerId(UUID customerId, UUID invoiceId);
    void deleteIncomeInvoiceForCustomerId(UUID customerId, UUID invoiceId);

    List<Invoice> listCostInvoices(UUID customerId);
    List<Invoice> listIncomeInvoices(UUID customerId);

}
