package org.example.domain.customer.TaxPayments;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public abstract class TaxPaymentForm {
    protected TaxRate taxRate;
}
