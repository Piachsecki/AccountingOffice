package org.example.domain.invoice;

import lombok.Getter;
import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.money.Price;
import org.example.domain.product.Product;

import java.time.OffsetDateTime;

@Getter
public class CostInvoice extends Invoice{
    private final InvoiceType invoiceType = InvoiceType.COST_INVOICE;
    private final Company company;
    private final Product product;
    private final Price amount;


    public CostInvoice(InvoiceId invoiceId, Customer customer, OffsetDateTime date, Price amount, Company company, Product product) {
        super(invoiceId, customer, date);
        this.company = company;
        this.product = product;
        this.amount = amount;
    }



    @Override
    public CostInvoice withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    @Override
    public String toString() {
        return "" + super.invoiceId;
    }
}
