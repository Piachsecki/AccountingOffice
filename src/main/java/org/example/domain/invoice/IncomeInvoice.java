package org.example.domain.invoice;

import lombok.Getter;
import org.example.domain.customer.Customer;
import org.example.domain.money.Income;

import java.time.OffsetDateTime;

@Getter
public class IncomeInvoice extends Invoice{
    private final InvoiceType invoiceType = InvoiceType.INCOME_INVOICE;

    private final Income amount;
    public IncomeInvoice(InvoiceId invoiceId, Customer customer, OffsetDateTime date, Income amount) {
        super(invoiceId, customer, date);
        this.amount = amount;
    }

    @Override
    public IncomeInvoice withCustomer(Customer customer) {
        super.customer = customer;
        return (IncomeInvoice) this;
    }
}
