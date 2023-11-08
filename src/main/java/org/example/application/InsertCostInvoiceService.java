package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.money.Price;
import org.example.domain.product.Product;
import org.example.port.in.invoice.InsertCostInvoiceUseCase;
import org.example.port.out.InvoiceRepository;

import java.time.OffsetDateTime;

@AllArgsConstructor
public class InsertCostInvoiceService implements InsertCostInvoiceUseCase {
    private InvoiceRepository invoiceRepository;
    @Override
    public Invoice insertCostInvoice(
            Customer customer,
            OffsetDateTime date,
            Price price,
            Company company,
            Product product
    ) {
        CostInvoice invoice = new CostInvoice(
                InvoiceId.createRandomInvoiceId(),
                customer,
                date,
                price,
                company,
                product
        );
        customer.insertCostInvoiceToCustomer(invoice);
        invoiceRepository.insertCostInvoice(invoice);

        return invoice;
    }

    @Override
    public Invoice insertCostInvoice(CostInvoice invoice) {
        invoice.getCustomer().insertCostInvoiceToCustomer(invoice);
        invoiceRepository.insertCostInvoice(invoice);

        return invoice;
    }
}
