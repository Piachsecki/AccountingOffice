package org.example.application;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountRevenueUseCase;
import org.example.port.out.InvoiceRepository;

import java.time.YearMonth;


@Slf4j
public class CountMonthlyRevenueService implements CountRevenueUseCase {
    private InvoiceRepository invoiceRepository;
    @Override
    public Money countMonthlyRevenueUseCase(CustomerId customerId, YearMonth yearMonth ) {
        if (yearMonth.isAfter(YearMonth.now())){
            log.error("We cannot calculate the revenue for the specified month [{}]," +
                    " because now we have [{}] and it is impossible to calculate" +
                    "revenue for the upcoming months!", yearMonth, YearMonth.now());
            throw new RuntimeException("We cannot calculate the revenue for the upcoming months!");
        }
        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue() - 1;
        return invoiceRepository.countMonthlyRevenueUseCase(customerId, YearMonth.of(year, month));
    }
}
