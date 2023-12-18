package org.example.domain.money;

public enum Currency {
    USD("4.15"),
    PLN("1"),
    EUR("4.42"),
    HUF("0.012");
    private final String exchange_rate;

    Currency(String exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

    public String getExchange_rate() {
        return exchange_rate;
    }
}
