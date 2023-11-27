package org.example.adapter.out.database.entity;

import jakarta.persistence.*;
import org.example.adapter.out.database.model.InvoiceDatabase;
import org.example.adapter.out.database.model.MoneyDatabase;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceType;
import org.example.domain.money.Money;

@Entity
@Table(name = "income_invoice")
public class IncomeInvoiceDatabaseEntity extends InvoiceDatabase {
    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private final InvoiceType invoiceType = InvoiceType.INCOME_INVOICE;

    @Embedded
    private MoneyDatabase amount;

    @ManyToOne
    private CustomerDatabaseEntity customerDatabaseEntity;

}
