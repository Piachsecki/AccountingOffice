package org.example.domain.customer.TaxPayments;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LumpSumTax extends TaxPaymentForm{

    public LumpSumTax(IndustryType industryType) {
        switch (industryType){
            case DOCTOR -> this.taxRate = TaxRate.LUMP_SUM15;
            case FARMER -> this.taxRate = TaxRate.LUMP_SUM5_5;
            case SOFTWARE_DEVELOPER -> this.taxRate = TaxRate.LUMP_SUM17;
            case TENANT -> this.taxRate = TaxRate.LUMP_SUM8_5;
            default -> {
                    log.error("A given profession {} is not supported in our accounting office!", industryType);
                    throw new IndustryTypeException(String.format(
                    "A given profession [%s] is not supported in our accounting office!", industryType
            )); }
        }
        log.debug("TaxRate: {} set successfully for a given profession: {}", taxRate, industryType);
    }

}
