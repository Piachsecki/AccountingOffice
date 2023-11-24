package org.example.domain.customer.TaxPayments;

import java.math.BigDecimal;

public class FlatTax extends TaxPaymentForm{
    public static BigDecimal FLAT_TAX_RATE = BigDecimal.valueOf(0.19);
    public FlatTax() {
        super(TaxRate.FLAT_TAX);
    }
}
