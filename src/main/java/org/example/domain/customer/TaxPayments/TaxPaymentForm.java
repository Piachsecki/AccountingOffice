package org.example.domain.customer.TaxPayments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class TaxPaymentForm {
    protected TaxRate taxRate;
}
