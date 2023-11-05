package org.example.domain.customer.TaxPayments;

public class FlatTax extends TaxPaymentForm{
    public FlatTax() {
        super(TaxRate.FLAT_TAX);
    }
}
