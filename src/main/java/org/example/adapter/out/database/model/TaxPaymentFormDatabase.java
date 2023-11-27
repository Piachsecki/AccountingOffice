package org.example.adapter.out.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import org.example.domain.customer.TaxPayments.TaxRate;

@MappedSuperclass
public abstract class TaxPaymentFormDatabase {
    @Enumerated(EnumType.STRING)
    @Column(name = "tax_rate")
    protected TaxRate taxRate;
}
