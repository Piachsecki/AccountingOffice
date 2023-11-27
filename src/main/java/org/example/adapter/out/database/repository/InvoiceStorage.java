package org.example.adapter.out.database.repository;

import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.money.Money;
import org.example.port.out.InvoiceRepository;

import java.time.YearMonth;
import java.util.HashSet;

public class InvoiceStorage implements InvoiceRepository {
    @Override
    public void insertInvoice(Invoice invoice) {

    }

    @Override
    public HashSet<Invoice> listAllInvoicesForCustomerId(CustomerId customerId) {
        return null;
    }

    @Override
    public void deleteInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId) {

    }

    @Override
    public void deleteAllInvoicesForCustomerId(CustomerId customerId) {

    }

    @Override
    public void deleteAllWithCustomer(CustomerId customerId) {

    }

    @Override
    public Money countMonthlyRevenueUseCase(CustomerId customerId, YearMonth monthToBeCounted) {
        return null;
    }

    @Override
    public Money countMonthlyCosts(CustomerId customerId, YearMonth yearMonth) {
        return null;
    }
}
