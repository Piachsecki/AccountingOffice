package org.example.domain.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.example.domain.customer.Customer;

import java.time.OffsetDateTime;


@AllArgsConstructor
@Getter
public abstract class Invoice {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice invoice)) return false;

        return getInvoiceId().equals(invoice.getInvoiceId());
    }

    @Override
    public int hashCode() {
        return getInvoiceId().hashCode();
    }

    protected final InvoiceId invoiceId;
    @With
    protected Customer customer;
    protected final OffsetDateTime date;

}
