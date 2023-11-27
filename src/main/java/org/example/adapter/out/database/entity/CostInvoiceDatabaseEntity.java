package org.example.adapter.out.database.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.adapter.out.database.model.InvoiceDatabase;
import org.example.domain.company.Company;
import org.example.domain.invoice.InvoiceType;
import org.example.domain.money.Price;
import org.example.domain.product.Product;


@Entity
@ToString(of = {"invoiceType", "product", "amount"})
@Table(name  = "cost_invoice")
public class CostInvoiceDatabaseEntity extends InvoiceDatabase {
    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private InvoiceType invoiceType = InvoiceType.COST_INVOICE;

    @Embedded
    private Price amount;

    @OneToOne
    private CompanyDatabaseEntity companyDatabaseEntity;
    @OneToOne
    private Product product;


}
