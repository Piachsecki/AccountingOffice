package org.example.port.out;

import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.money.Money;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface InvoiceRepository {
    void insertInvoice(CustomerId customerId, Invoice invoice);

    HashSet<Invoice> listAllInvoicesForCustomerId(CustomerId customerId);

    void deleteInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId);

    void deleteAllInvoicesForCustomerId(CustomerId customerId);


    void deleteAllWithCustomer(CustomerId customerId);
    List<Invoice> listCostInvoices(CustomerId customerId);
    List<Invoice> listIncomeInvoices(CustomerId customerId);

}
