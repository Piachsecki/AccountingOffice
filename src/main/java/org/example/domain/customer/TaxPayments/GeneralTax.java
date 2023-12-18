package org.example.domain.customer.TaxPayments;

import java.math.BigDecimal;

public class GeneralTax extends TaxPaymentForm {
    public static final String FIRST_SCALE = "0.12";
    public static final String SECOND_SCALE = "0.32";
    public static final BigDecimal SCALE_SALARY = BigDecimal.valueOf(5000);

    public GeneralTax() {
        super(TaxRate.GENERAL_TAX);
    }

    @Override
    public String toString() {
        return "GeneralTax";
    }
}
