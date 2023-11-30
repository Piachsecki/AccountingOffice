package org.example.domain.customer;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.example.application.exceptions.CountHealthInsuranceContributionException;
import org.example.application.exceptions.CountIncomeTaxException;
import org.example.application.exceptions.CountMonthlyCostsException;
import org.example.application.exceptions.CountMonthlyIncomeException;
import org.example.domain.Address;
import org.example.domain.NIP;
import org.example.domain.customer.TaxPayments.*;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import static org.example.Data.*;
import static org.example.domain.customer.TaxPayments.FlatTax.FLAT_TAX_RATE;
import static org.example.domain.customer.TaxPayments.HEALTH_INSURANCE_RANGE_FOR_LUMP_SUM.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Customer {
    @EqualsAndHashCode.Include
    private CustomerId customerId;
    private String name;
    private String surname;
    private NIP nip;
    private Address address;
    private OffsetDateTime joinDate;
    private Entrepreneurship entrepreneurshipForm;
    private List<IncomeInvoice> incomeInvoices;
    private List<CostInvoice> costInvoices;

    public Customer(String name, String surname, NIP nip, Address address, OffsetDateTime joinDate, Entrepreneurship entrepreneurshipForm, List<IncomeInvoice> incomeInvoices, List<CostInvoice> costInvoices) {
        this.name = name;
        this.surname = surname;
        this.nip = nip;
        this.address = address;
        this.joinDate = joinDate;
        this.entrepreneurshipForm = entrepreneurshipForm;
        this.incomeInvoices = incomeInvoices;
        this.costInvoices = costInvoices;
    }

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
    public Money countMonthlyRevenue(YearMonth yearMonth) {
        if (yearMonth.isAfter(YearMonth.now())) {
            log.error("We cannot calculate the revenue for the specified month [{}]," +
                    " because now we have [{}] and it is impossible to calculate" +
                    "revenue for the upcoming months!", yearMonth, YearMonth.now());
            throw new RuntimeException("We cannot calculate the revenue for the upcoming months!");
        }

        BigDecimal monthlyRevenue = incomeInvoices.stream()
                .filter(date -> yearMonth
                        .equals(
                                YearMonth.of(
                                        date.getDate().getYear(),
                                        date.getDate().getMonth()

                                )))
                .map(invoice -> invoice.getAmount().amount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return new Money(monthlyRevenue, Currency.PLN);
    }

    public Money countMonthlyCosts(YearMonth yearMonth) {
        if (yearMonth.isAfter(YearMonth.now())) {
            log.error("We cannot calculate the revenue for the specified month [{}]," +
                    " because now we have [{}] and it is impossible to calculate" +
                    "revenue for the upcoming months!", yearMonth, YearMonth.now());
            throw new CountMonthlyCostsException("We cannot calculate the revenue for the upcoming months!");
        }
        BigDecimal monthlyCosts = costInvoices.stream()
                .filter(date -> yearMonth
                        .equals(
                                YearMonth.of(
                                        date.getDate().getYear(),
                                        date.getDate().getMonth()

                                )))
                .map(invoice -> invoice.getAmount().amount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return new Money(monthlyCosts, Currency.PLN);
    }

    public Money countMonthlyIncome(YearMonth yearMonth) {
        if (yearMonth.isAfter(YearMonth.now())) {
            log.error("We cannot calculate the revenue for the specified month [{}]," +
                    " because now we have [{}] and it is impossible to calculate" +
                    "revenue for the upcoming months!", yearMonth, YearMonth.now());
            throw new CountMonthlyIncomeException("We cannot calculate the revenue for the upcoming months!");
        }

        Money monthlyRevenue = countMonthlyRevenue(yearMonth);
        Money monthlyCosts = countMonthlyCosts(yearMonth);
        Money tax = countIncomeTax(yearMonth);
        BigDecimal finalIncome = monthlyRevenue.amount().subtract(monthlyCosts.amount()).subtract(tax.amount());
        return new Money(finalIncome, Currency.PLN);

    }

    public Money countIncomeTax(YearMonth yearMonth) {
        Money monthlyRevenue = countMonthlyRevenue(yearMonth);
        Money monthlyCosts = countMonthlyCosts(yearMonth);

        BigDecimal incomeBeforeTaxIncludingSocialContributions = countIncomeBeforeTaxIncludingSocialContributions(monthlyRevenue, monthlyCosts);
        TaxPaymentForm customerTaxPaymentForm = getCustomerTaxPaymentForm(this);

        if (customerTaxPaymentForm instanceof FlatTax) {
            return countIncomeTaxForFlatTax(incomeBeforeTaxIncludingSocialContributions);
        } else if (customerTaxPaymentForm instanceof GeneralTax) {
            return countIncomeTaxForGeneralTax(incomeBeforeTaxIncludingSocialContributions);
        } else if (customerTaxPaymentForm instanceof LumpSumTax) {
            return countIncomeTaxForLumpSum(incomeBeforeTaxIncludingSocialContributions, yearMonth);

        }

        log.error("Cannot calculate incomeTax for [{}]", this);
        throw new CountIncomeTaxException(
                String.format("Cannot calculate incomeTax for [%s]", this.getCustomerId())
        );

    }

    private BigDecimal countIncomeBeforeTaxIncludingSocialContributions(Money monthlyRevenue, Money monthlyCosts) {
        return monthlyRevenue.amount().subtract(monthlyCosts.amount()).subtract(SOCIAL_CONTRIBUTION);
    }

    private Money countIncomeTaxForGeneralTax(BigDecimal incomeBeforeTax) {
        if (incomeBeforeTax.compareTo(GeneralTax.SCALE_SALARY) <= 0) {
            return new Money(
                    incomeBeforeTax
                            .multiply(new BigDecimal(GeneralTax.FIRST_SCALE)),
                    Currency.PLN);
        } else {
            return new Money(
                    incomeBeforeTax
                            .multiply(new BigDecimal(GeneralTax.SECOND_SCALE)),
                    Currency.PLN);
        }
    }

    private Money countIncomeTaxForFlatTax(BigDecimal incomeBeforeTax) {
        return new Money(
                incomeBeforeTax
                        .multiply(FLAT_TAX_RATE),
                Currency.PLN);
    }

    private TaxPaymentForm getCustomerTaxPaymentForm(Customer customer) {
        return customer.getEntrepreneurshipForm().taxPaymentForm();
    }

    private Money countIncomeTaxForLumpSum(BigDecimal incomeBeforeTax, YearMonth yearMonth) {
        BigDecimal lumpSumTaxRate = new BigDecimal(this.entrepreneurshipForm.taxPaymentForm().getTaxRate().getValue());

        incomeBeforeTax
                = subtractHealthContributionRateFromRevenue(incomeBeforeTax, calculateHealthInsuranceContribution(yearMonth));

        return countTheIncomeTaxForLumpSum(incomeBeforeTax, lumpSumTaxRate);
    }

    private BigDecimal subtractHealthContributionRateFromRevenue(BigDecimal incomeBeforeTax, Money healthInsuranceContribution) {
        return incomeBeforeTax.subtract(healthInsuranceContribution.amount());
    }


    public Money calculateHealthInsuranceContribution(YearMonth yearMonth) {
        TaxPaymentForm customerTaxPaymentForm = this.getCustomerTaxPaymentForm(this);

        if (customerTaxPaymentForm instanceof LumpSumTax) {
            return countHealthInsuranceForLumpSumTax(yearMonth);
        }
        if (customerTaxPaymentForm instanceof FlatTax) {
            return countHealthInsuranceForFlatTax(yearMonth);
        }
        if (customerTaxPaymentForm instanceof GeneralTax) {
            return countHealthInsuranceForGeneralTax(yearMonth);
        }

        log.error("The given taxPaymentForm {} is wrong!", customerTaxPaymentForm);
        throw new CountHealthInsuranceContributionException(
                String.format("Given taxPaymentForm:[%s] is wrong" +
                        "!", customerTaxPaymentForm));
    }

    private Money countHealthInsuranceForGeneralTax(YearMonth yearMonth) {
        Money monthlyIncome = countMonthlyIncome(
                        yearMonth
                );
        if (checkIfIncomeIsLessThanMinimumWage(monthlyIncome)) {
            return new Money(
                    MINIMUM_WAGE.multiply(STANDARD_CONTRIBUTION_HEALTH_RATE),
                    Currency.PLN
            );

        } else {
            return new Money(
                    monthlyIncome.amount()
                            .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE),
                    Currency.PLN);
        }
    }

    private Money countHealthInsuranceForFlatTax(YearMonth yearMonth) {
        Money monthlyIncome = countMonthlyIncome(yearMonth);
        if (checkIncomeRangeForFlatTax(monthlyIncome)) {
            return new Money(
                    MINIMUM_WAGE
                            .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE)
                    , Currency.PLN
            );

        } else {
            return new Money(
                    monthlyIncome
                            .amount()
                            .multiply(
                                    CONTRIBUTION_HEALTH_RATE_FOR_FLAT_TAX),
                    Currency.PLN
            );
        }
    }

    private Money countHealthInsuranceForLumpSumTax(YearMonth yearMonth) {
        BigDecimal monthlyRevenue = countMonthlyRevenue(
                yearMonth
        )
                .amount();

        switch (checkTheRevenueRangeForHealthInsuranceTax(monthlyRevenue)) {

            case FIRST_RANGE -> {
                return new Money(
                        monthlyRevenue
                                .multiply(BELOW_CONTRIBUTION_HEALTH_RATE)
                                .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE)
                        , Currency.PLN);
            }
            case SECOND_RANGE -> {
                return new Money(
                        monthlyRevenue
                                .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE),
                        Currency.PLN);

            }
            case THIRD_RANGE -> {
                return new Money(
                        monthlyRevenue
                                .multiply(OVER_CONTRIBUTION_HEALTH_RATE)
                                .multiply(STANDARD_CONTRIBUTION_HEALTH_RATE),
                        Currency.PLN);
            }
        }

        log.error("Cannot calculate the insurance for LumpSumTax!");
        throw new CountHealthInsuranceContributionException(
                "Cannot calculate the insurance for LumpSumTax!"
        );

    }



    private static Money countTheIncomeTaxForLumpSum(BigDecimal incomeBeforeTax, BigDecimal lumpSumTaxRate) {
        return new Money(
                incomeBeforeTax
                        .multiply(lumpSumTaxRate),
                Currency.PLN
        );
    }

    private static boolean checkIfIncomeIsLessThanMinimumWage(Money monthlyIncome) {
        return monthlyIncome.amount().compareTo(MINIMUM_WAGE) <= 0;
    }

    private static boolean checkIncomeRangeForFlatTax(Money monthlyIncome) {
        return monthlyIncome.amount().compareTo(MINIMUM_WAGE) <= 0;
    }

    private static HEALTH_INSURANCE_RANGE_FOR_LUMP_SUM checkTheRevenueRangeForHealthInsuranceTax(BigDecimal monthlyRevenue) {
        if (monthlyRevenue.compareTo(FIRST_CONTRIBUTION_RANGE) < 0) {
            return FIRST_RANGE;
        } else if (monthlyRevenue.compareTo(FIRST_CONTRIBUTION_RANGE) >= 0 &&
                monthlyRevenue.compareTo(SECOND_CONTRIBUTION_RANGE) < 0) {
            return SECOND_RANGE;
        } else {
            return THIRD_RANGE;
        }

    }





}









