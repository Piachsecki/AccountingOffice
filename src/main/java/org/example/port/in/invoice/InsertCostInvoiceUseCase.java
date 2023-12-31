package org.example.port.in.invoice;

import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.money.Price;
import org.example.domain.product.Product;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface InsertCostInvoiceUseCase {
    Invoice insertCostInvoice(
            Customer customer,
            OffsetDateTime date,
            Price price,
            Company company,
            Product product
    );

    Invoice insertCostInvoice(UUID customerId, CostInvoice invoice);
}
