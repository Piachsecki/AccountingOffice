package org.example.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.application.exceptions.CountMonthlyRevenueException;
import org.example.domain.customer.CustomerId;
import org.example.domain.money.Money;
import org.example.port.in.invoice.CountMonthlyRevenueUseCase;
import org.example.port.out.InvoiceRepository;

import java.time.YearMonth;


@Slf4j
@AllArgsConstructor
public class CountMonthlyMonthlyRevenueService implements CountMonthlyRevenueUseCase {
    private InvoiceRepository invoiceRepository;
    @Override
    public Money countMonthlyRevenue(CustomerId customerId, YearMonth yearMonth ) {
        if (yearMonth.isAfter(YearMonth.now())){
            log.error("We cannot calculate the revenue for the specified month [{}]," +
                    " because now we have [{}] and it is impossible to calculate" +
                    "revenue for the upcoming months!", yearMonth, YearMonth.now());
            throw new CountMonthlyRevenueException("We cannot calculate the revenue for the upcoming months!");
        }
        return invoiceRepository.countMonthlyRevenueUseCase(customerId, yearMonth);
    }
}
