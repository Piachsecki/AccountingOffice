package org.example.port.in.invoice;

import org.example.domain.customer.Customer;
import org.example.domain.money.Money;

import java.time.YearMonth;

public interface CountIncomeTaxUseCase {
    Money countIncomeTax(Customer customer, YearMonth yearMonth);
}
