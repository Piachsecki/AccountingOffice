package org.example.domain.invoice;

import lombok.Getter;
import org.example.domain.customer.Customer;
import org.example.domain.money.Money;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class IncomeInvoice extends Invoice {
    private final InvoiceType invoiceType = InvoiceType.INCOME_INVOICE;

    private Money amount;

    public IncomeInvoice(UUID invoiceId, Customer customer, OffsetDateTime date, Money amount) {
        super(invoiceId, customer, date);
        this.amount = amount;
    }

    public IncomeInvoice withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public IncomeInvoice withAmount(Money amount) {
        this.amount = amount;
        return this;
    }

    public IncomeInvoice withInvoiceId(UUID invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }
}
