package org.example.application;

import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.money.Price;
import org.example.domain.product.Product;
import org.example.port.in.customer.CreateInvoiceUseCase;
import org.example.port.out.InvoiceRepository;

import java.time.OffsetDateTime;


public class CreateInvoiceService implements CreateInvoiceUseCase {
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice createInvoice(Customer customer, Price price, Product product, Company company, OffsetDateTime date) {
        Invoice invoice = new Invoice(
                InvoiceId.createRandomInvoiceId(),
                customer,
                company,
                product,
                date
        );
        invoiceRepository.createInvoice(invoice);

        return invoice;
    }
}
