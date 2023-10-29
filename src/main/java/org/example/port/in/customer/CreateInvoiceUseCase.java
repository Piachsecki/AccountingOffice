package org.example.port.in.customer;

import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.Invoice;
import org.example.domain.money.Price;
import org.example.domain.product.Product;

import java.time.OffsetDateTime;

public interface CreateInvoiceUseCase {
    Invoice createInvoice(Customer customer, Price price, Product product, Company company, OffsetDateTime date);
}
