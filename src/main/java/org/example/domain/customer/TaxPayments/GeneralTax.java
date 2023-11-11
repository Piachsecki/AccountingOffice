package org.example.domain.customer.TaxPayments;

import java.math.BigDecimal;

public class GeneralTax extends TaxPaymentForm{
    //dla miesiecznie 10k <=
    public static final String FIRST_SCALE = "12";
    //dla miesiecznie 10k >
    public static final String SECOND_SCALE = "32";
    public static final BigDecimal SCALE_SALARY = BigDecimal.valueOf(5000);
    public GeneralTax() {
        super(TaxRate.GENERAL_TAX);
    }
}
