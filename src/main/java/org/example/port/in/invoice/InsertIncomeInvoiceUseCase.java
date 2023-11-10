package org.example.port.in.invoice;

import org.example.domain.customer.Customer;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.money.Money;

import java.time.OffsetDateTime;

public interface InsertIncomeInvoiceUseCase {
    Invoice insertIncomeInvoice(
            Customer customer,
            OffsetDateTime date,
            Money amount
    );

    Invoice insertIncomeInvoice(IncomeInvoice invoice);
}
