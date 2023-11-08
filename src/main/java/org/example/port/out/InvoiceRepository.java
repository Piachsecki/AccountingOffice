package org.example.port.out;

import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;

import java.util.HashSet;

public interface InvoiceRepository {
    void insertCostInvoice(Invoice invoice);

    HashSet<Invoice> listAllInvoicesForCustomerId(CustomerId customerId);

    void deleteInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId);

    void deleteAllInvoicesForCustomerId(CustomerId customerId);

    void insertIncomeInvoice(Invoice invoice);

    void deleteAllWithCustomer(CustomerId customerId);
}
