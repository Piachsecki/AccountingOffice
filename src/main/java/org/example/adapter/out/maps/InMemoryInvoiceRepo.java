package org.example.adapter.out.maps;

import lombok.Getter;
import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.example.port.out.InvoiceRepository;

import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class InMemoryInvoiceRepo implements InvoiceRepository {
    // maybe a list of invoices in here?
    private final Map<CustomerId, HashSet<Invoice>> invoices = new HashMap<>();

    @Override
    public void insertInvoice(Invoice invoice) {
        if (!invoices.containsKey(invoice.getCustomer().getCustomerId())){
            invoices.put(invoice.getCustomer().getCustomerId(), new HashSet<>(Set.of(invoice)));
        } else {
            HashSet<Invoice> newHashSet = new HashSet<>(invoices.get(invoice.getCustomer().getCustomerId()).size() + 1);

            for (Map.Entry<CustomerId, HashSet<Invoice>> customerIdHashSetEntry : invoices.entrySet()) {
                newHashSet.addAll(customerIdHashSetEntry.getValue());
            }
            newHashSet.add(invoice);
            invoices.put(
                    invoice.getCustomer().getCustomerId(), newHashSet);
        }
    }

    @Override
    public HashSet<Invoice> listAllInvoicesForCustomerId(CustomerId customerId) {
        return invoices.get(customerId);
    }

    @Override
    public void deleteInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId) {
        invoices.get(customerId).removeIf(invoice -> invoice.getInvoiceId().equals(invoiceId));
    }

    @Override
    public void deleteAllInvoicesForCustomerId(CustomerId customerId) {
        invoices.get(customerId).clear();
    }



    @Override
    public void deleteAllWithCustomer(CustomerId customerId) {
        invoices.get(customerId).clear();
        invoices.clear();
    }

}
