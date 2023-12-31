package org.example;

import org.example.domain.Address;
import org.example.domain.NIP;
import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.customer.Entrepreneurship;
import org.example.domain.customer.EntrepreneurshipForm;
import org.example.domain.customer.TaxPayments.FlatTax;
import org.example.domain.customer.TaxPayments.IndustryType;
import org.example.domain.customer.TaxPayments.LumpSumTax;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.money.Currency;
import org.example.domain.money.Money;
import org.example.domain.money.Price;
import org.example.domain.product.Product;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.UUID;

public class DataCreator {
    public static Customer createCustomer1() {
        return new Customer(
                UUID.randomUUID(),
                "Kacper",
                "Piasecki",
                new NIP("1462693747"),
                createAddress1(),
                OffsetDateTime.of(
                        2021,
                        12, 17, 23, 40, 12, 23, ZoneOffset.UTC
                ),
                new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new FlatTax()
                ),
                new ArrayList<>(),
                new ArrayList<>()

        );
    }

    private static Address createAddress1() {
        return new Address(
                "Szczecin",
                "Poland",
                "Rdzinna 12",
                "72-897");
    }

    private static Address createAddress2() {
        return new Address(
                "Police",
                "Poland",
                "Chciwa 54",
                "72-897");
    }

    private static Address createAddress3() {
        return new Address(
                "Bialystok",
                "Poland",
                "Lwa 2",
                "02-877");
    }

    private static Address createAddress4() {
        return new Address(
                "Warszawa",
                "Poland",
                "Maczka 75",
                "54-125");
    }

    public static Customer createCustomer2() {
        return new Customer(
                UUID.randomUUID(),
                "Kacper",
                "Maslany",
                new NIP("9527816928"),
                createAddress2(),
                OffsetDateTime.of(
                        2022, 12, 17, 23, 40, 12, 23, ZoneOffset.UTC
                ),
                new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public static Customer createCustomer3() {
        return new Customer(
                UUID.randomUUID(),
                "Karolina",
                "Koralowa",
                new NIP("0003768420"),
                createAddress3(),
                OffsetDateTime.of(
                        2023, 12, 17, 23, 40, 12, 23, ZoneOffset.UTC
                ),
                new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ),
                new ArrayList<>(),
                new ArrayList<>()

        );
    }


    public static Customer createCustomer4() {
        return new Customer(
                UUID.randomUUID(),
                "Kacper",
                "Piasecki",
                new NIP("1461113747"),
                createAddress4(),
                OffsetDateTime.of(
                        2024, 12, 17, 23, 40, 12, 23, ZoneOffset.UTC
                ),
                new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ),
                new ArrayList<>(),
                new ArrayList<>()

        );
    }


    public static Customer createCustomer5() {
        return new Customer(
                UUID.randomUUID(),
                "Wojtek",
                "Marciniak",
                new NIP("4627890357"),
                createAddress5(),
                OffsetDateTime.of(
                        2025, 12, 17, 23, 40, 12, 23, ZoneOffset.UTC
                ),
                new Entrepreneurship(
                        EntrepreneurshipForm.SOLE_PROPRIETORSHIP,
                        new LumpSumTax(IndustryType.SOFTWARE_DEVELOPER)
                ),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    private static Address createAddress5() {
        return new Address(
                "Wroclaw",
                "Poland",
                "Polna 1",
                "87-297");
    }


    public static CostInvoice createCostInvoice1() {
        return new CostInvoice(
                UUID.randomUUID(),
                createCustomer1(),
                OffsetDateTime.of(
                        2022, 10, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC),
                new Price(new BigDecimal("152.23"), Currency.PLN),
                createCompany1(),
                createProduct1()
        );
    }

    public static CostInvoice createCostInvoice2() {
        return new CostInvoice(
                UUID.randomUUID(),
                createCustomer2(),
                OffsetDateTime.of(
                        2021, 12, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC),
                new Price(new BigDecimal("222.23"), Currency.PLN),
                createCompany2(),
                createProduct2()
        );
    }

    public static CostInvoice createCostInvoice3() {
        return new CostInvoice(
                UUID.randomUUID(),
                createCustomer3(),

                OffsetDateTime.of(
                        2022, 12, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC),
                new Price(new BigDecimal("333.23"), Currency.PLN),createCompany3(),
                createProduct3()

        );
    }

    public static CostInvoice createCostInvoice4() {
        return new CostInvoice(
                UUID.randomUUID(),
                createCustomer4(),
                OffsetDateTime.of(
                        2023, 12, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC),
                new Price(new BigDecimal("444.23"), Currency.PLN),
                createCompany4(),
                createProduct4()

        );
    }

    public static CostInvoice createCostInvoice5() {
        return new CostInvoice(
                UUID.randomUUID(),
                createCustomer5(),
                OffsetDateTime.of(
                        2024, 12, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC),
                new Price(new BigDecimal("555.23"), Currency.PLN),
                createCompany5(),
                createProduct5()


        );
    }

    public static Product createProduct1() {
        return new Product(
                UUID.randomUUID(),
                "zabawka",
                new Price(new BigDecimal("123.23"), Currency.PLN)
        );
    }

    public static Product createProduct2() {
        return new Product(
                UUID.randomUUID(),
                "obiad",
                new Price(new BigDecimal("4211"), Currency.EUR)
        );
    }

    public static Product createProduct3() {
        return new Product(
                UUID.randomUUID(),
                "metal",
                new Price(new BigDecimal("1.25"), Currency.USD)
        );
    }

    public static Product createProduct4() {
        return new Product(
                UUID.randomUUID(),
                "ciastko",
                new Price(new BigDecimal("15000"), Currency.HUF)
        );
    }

    public static Product createProduct5() {
        return new Product(
                UUID.randomUUID(),
                "zabawka",
                new Price(new BigDecimal("123.23"), Currency.PLN)
        );
    }


    public static Company createCompany1() {
        return new Company(
                "BOSCH",
                new NIP("4328792153"),
                createAddress1()
        );
    }

    public static Company createCompany2() {
        return new Company(
                "BOSCH",
                new NIP("8888777762"),
                createAddress2()
        );
    }

    public static Company createCompany3() {
        return new Company(
                "Samsung",
                new NIP("1223334490"),
                createAddress3()
        );
    }

    public static Company createCompany4() {
        return new Company(
                "Apple",
                new NIP("2900182654"),
                createAddress4()
        );
    }

    public static Company createCompany5() {
        return new Company(
                "Edifier",
                new NIP("2200997452"),
                createAddress5()
        );
    }


    public static IncomeInvoice createIncomeInvoice1() {
        return new IncomeInvoice(
                UUID.randomUUID(),
                createCustomer1(),
                OffsetDateTime.of(
                        2020, 10, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC),
                new Money(new BigDecimal("8000"), Currency.PLN)
        );
    }
    public static IncomeInvoice createIncomeInvoice2() {
        return new IncomeInvoice(
                UUID.randomUUID(),
                createCustomer1(),
                OffsetDateTime.of(
                        2020, 10, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC),
                new Money(new BigDecimal("10000"), Currency.PLN)
        );

    }
    public static IncomeInvoice createIncomeInvoice3() {
        return new IncomeInvoice(
                UUID.randomUUID(),
                createCustomer3(),
                OffsetDateTime.of(
                        2020, 10, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC),
                new Money(new BigDecimal("15000"), Currency.PLN)
        );

    }
    public static IncomeInvoice createIncomeInvoice4() {
        return new IncomeInvoice(
                UUID.randomUUID(),
                createCustomer4(),
                OffsetDateTime.of(
                        2020, 10, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC),
                new Money(new BigDecimal("20000"), Currency.PLN)
        );

    }


    public static IncomeInvoice createIncomeInvoice5() {
        return new IncomeInvoice(
                UUID.randomUUID(),
                createCustomer5(),
                OffsetDateTime.of(
                        2020, 10, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC),
                new Money(new BigDecimal("30000"), Currency.PLN)
        );

    }
}
