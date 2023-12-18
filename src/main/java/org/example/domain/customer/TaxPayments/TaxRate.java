package org.example.domain.customer.TaxPayments;

public enum TaxRate {
    FLAT_TAX("0.19"),
    GENERAL_TAX("0.17"),
    LUMP_SUM2("0.02"),
    LUMP_SUM3("0.03"),
    LUMP_SUM5_5("0.055"),
    LUMP_SUM8_5("0.085"),
    LUMP_SUM10("0.10"),
    LUMP_SUM12("0.12"),
    LUMP_SUM12_5("0.125"),
    LUMP_SUM14("0.14"),
    LUMP_SUM15("0.15"),
    LUMP_SUM17("0.17");

    public String getValue() {
        return value;
    }

    private final String value;

    TaxRate(String value) {
        this.value = value;
    }
}
