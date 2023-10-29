package org.example.domain.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.product.Product;

import java.time.OffsetDateTime;


@AllArgsConstructor
public class Invoice {
    private InvoiceId invoiceId;
    @Getter
    private final Customer customer;
    private final Company company;
    private final Product product;
    private final OffsetDateTime date;

}
