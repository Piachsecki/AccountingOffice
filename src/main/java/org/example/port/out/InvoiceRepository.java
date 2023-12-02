package org.example.port.out;

import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;

import java.util.HashSet;
import java.util.List;

public interface InvoiceRepository {
    Invoice insertInvoice(CustomerId customerId, Invoice invoice);

    HashSet<Invoice> listAllInvoicesForCustomerId(CustomerId customerId);

    void deleteCostInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId);
    void deleteIncomeInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId);

    List<Invoice> listCostInvoices(CustomerId customerId);
    List<Invoice> listIncomeInvoices(CustomerId customerId);

}
