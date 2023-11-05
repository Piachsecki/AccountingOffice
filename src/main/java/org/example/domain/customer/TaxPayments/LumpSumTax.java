package org.example.domain.customer.TaxPayments;

public class LumpSumTax extends TaxPaymentForm{

    public LumpSumTax(IndustryType industryType) {
        switch (industryType){
            case DOCTOR -> this.taxRate = TaxRate.LUMP_SUM15;
            case FARMER -> this.taxRate = TaxRate.LUMP_SUM5;
            case SOFTWARE_DEVELOPER -> this.taxRate = TaxRate.LUMP_SUM17;
            case TENANT -> this.taxRate = TaxRate.LUMP_SUM8;
            default -> throw new IndustryTypeException(String.format(
                    "A given profession [%s] is not supported in our accounting office!", industryType
            ));
        }
    }
}
