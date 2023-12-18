package org.example.application;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.money.Price;
import org.example.domain.product.Product;
import org.example.port.in.invoice.InsertCostInvoiceUseCase;
import org.example.port.out.InvoiceRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class InsertCostInvoiceService implements InsertCostInvoiceUseCase {
    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice insertCostInvoice(
            Customer customer,
            OffsetDateTime date,
            Price price,
            Company company,
            Product product
    ) {
        CostInvoice invoice = new CostInvoice(
                UUID.randomUUID(),
                customer,
                date,
                price,
                company,
                product
        );
        customer.insertCostInvoiceToCustomer(invoice);
        invoiceRepository.insertInvoice(customer.getCustomerId(), invoice);

        return invoice;
    }

    @Override
    public Invoice insertCostInvoice(UUID customerId, CostInvoice invoice) {
        invoice.getCustomer().getCostInvoices().add(invoice);
        invoiceRepository.insertInvoice(customerId, invoice);
        return invoice;
    }
}
