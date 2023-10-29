package org.example.adapter.out;

import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.Invoice;
import org.example.port.out.InvoiceRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryInvoiceRepo implements InvoiceRepository {
    // maybe a list of invoices in here?
    Map<CustomerId, Invoice> invoices = new HashMap<>();

    @Override
    public void createInvoice(Invoice invoice) {
        invoices.put(invoice.getCustomer().getCustomerId(), invoice);
    }
}
