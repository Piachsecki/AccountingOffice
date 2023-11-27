package org.example.adapter.out.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.InvoiceId;

import java.time.OffsetDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class InvoiceDatabase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "invoice_id")
    protected UUID invoiceId;

    protected OffsetDateTime date;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    protected CustomerDatabaseEntity customer;
}
