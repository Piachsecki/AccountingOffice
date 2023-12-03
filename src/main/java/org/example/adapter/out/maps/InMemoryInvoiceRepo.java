package org.example.adapter.out.maps;

import lombok.Getter;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.port.out.InvoiceRepository;

import java.util.*;

@Getter
public class InMemoryInvoiceRepo implements InvoiceRepository {
    // maybe a list of invoices in here?
    private final Map<UUID, HashSet<Invoice>> invoices = new HashMap<>();

    @Override
    public Invoice insertInvoice(UUID customerId, Invoice invoice) {
        if (!invoices.containsKey(customerId)){
            invoices.put(customerId, new HashSet<>(Set.of(invoice)));
        } else {
            HashSet<Invoice> newHashSet = new HashSet<>(invoices.get(customerId).size() + 1);

            for (Map.Entry<UUID, HashSet<Invoice>> customerIdHashSetEntry : invoices.entrySet()) {
                newHashSet.addAll(customerIdHashSetEntry.getValue());
            }
            newHashSet.add(invoice);
            invoices.put(
                    customerId, newHashSet);
        }
        return invoice;
    }

    @Override
    public HashSet<Invoice> listAllInvoicesForCustomerId(UUID customerId) {
        return invoices.get(customerId);
    }

    @Override
    public void deleteCostInvoiceForCustomerId(UUID customerId, UUID invoiceId) {
        invoices.get(customerId).removeIf(invoice -> invoice.getInvoiceId().equals(invoiceId));
    }

    @Override
    public void deleteIncomeInvoiceForCustomerId(UUID customerId, UUID invoiceId) {
        invoices.get(customerId).removeIf(invoice -> invoice.getInvoiceId().equals(invoiceId));
    }

    @Override
    public List<Invoice> listCostInvoices(UUID customerId) {
        return invoices.get(customerId).stream()
                .filter(x -> x instanceof CostInvoice)
                .toList();
    }

    @Override
    public List<Invoice> listIncomeInvoices(UUID customerId) {
        return invoices.get(customerId).stream()
                .filter(x -> x instanceof IncomeInvoice)
                .toList();
    }

}
