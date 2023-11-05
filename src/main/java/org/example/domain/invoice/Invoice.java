package org.example.domain.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.example.domain.customer.Customer;

import java.time.OffsetDateTime;


@AllArgsConstructor
@Getter
public abstract class Invoice {
    protected final InvoiceId invoiceId;
    @With
    protected Customer customer;
    protected final OffsetDateTime date;

}
