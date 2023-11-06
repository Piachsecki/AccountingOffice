package org.example.domain.invoice;

import lombok.Getter;
import lombok.ToString;
import lombok.With;
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

    public CostInvoice(InvoiceId invoiceId, Customer customer, Company company, Product product, OffsetDateTime date, Price amount) {
        super(invoiceId, customer, date);
        this.company = company;
        this.product = product;
        this.amount = amount;
    }

    @Override
    public CostInvoice withCustomer(Customer customer) {
        super.customer = customer;
        return (CostInvoice)this;
    }

    @Override
    public String toString() {
        return "CostInvoice{" +
                "invoiceType=" + invoiceType +
                ", company=" + company +
                ", product=" + product +
                ", amount=" + amount +
                ", invoiceId=" + invoiceId +
                ", customer=" + customer +
                ", date=" + date +
                '}';
    }
}
