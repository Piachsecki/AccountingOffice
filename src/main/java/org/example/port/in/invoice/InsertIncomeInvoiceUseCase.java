package org.example.port.in.invoice;

import org.example.domain.customer.Customer;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.money.Income;

import java.time.OffsetDateTime;

public interface InsertIncomeInvoiceUseCase {
    Invoice insertIncomeInvoice(
            Customer customer,
            OffsetDateTime date,
            Income amount
    );

    Invoice insertIncomeInvoice(IncomeInvoice invoice);
}
