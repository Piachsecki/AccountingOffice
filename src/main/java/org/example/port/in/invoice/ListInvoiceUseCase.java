package org.example.port.in.invoice;

import org.example.domain.invoice.Invoice;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ListInvoiceUseCase {
    Set<Invoice> listAllInvoicesForCustomerId(UUID customerId);

    List<Invoice> listCostInvoices(UUID customerId);

    List<Invoice> listIncomeInvoices(UUID customerId);
}
