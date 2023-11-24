package org.example.domain.customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Address;
import org.example.domain.NIP;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.List;

@AllArgsConstructor
@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Customer {
    @EqualsAndHashCode.Include

    private CustomerId customerId;
    private String name;
    private String surname;
    private NIP nip;
    private Address address;
    private final OffsetDateTime joinDate;
    private Entrepreneurship entrepreneurshipForm;
    private List<IncomeInvoice> incomeInvoices;
    private List<CostInvoice> costInvoices;


    public void insertIncomeInvoiceToCustomer(IncomeInvoice invoice) {
        incomeInvoices.add(invoice);

    }

    public void insertCostInvoiceToCustomer(CostInvoice invoice) {
        costInvoices.add(invoice);
    }

    public Customer withEntrepreneurshipForm(Entrepreneurship entrepreneurshipForm) {
        this.entrepreneurshipForm = entrepreneurshipForm;
        return this;

    }


    //this will stay so, to discuss the importance of the CountMonthlyRevenueService
    public Money countMonthlyRevenue(CustomerId customerId, YearMonth yearMonth) {
        if (yearMonth.isAfter(YearMonth.now())) {
            log.error("We cannot calculate the revenue for the specified month [{}]," +
                    " because now we have [{}] and it is impossible to calculate" +
                    "revenue for the upcoming months!", yearMonth, YearMonth.now());
            throw new RuntimeException("We cannot calculate the revenue for the upcoming months!");
        }

        BigDecimal revenue = BigDecimal.valueOf(incomeInvoices.stream()
                .filter(date -> yearMonth
                        .equals(
                                YearMonth.of(
                                        date.getDate().getYear(),
                                        date.getDate().getMonth()

                                )))
                .map(x -> x.getAmount().amount())
                .count()
        );


        return new Money(revenue, Currency.PLN);
    }



}
