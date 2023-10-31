package org.example.application;

import org.example.domain.Address;
import org.example.domain.NIP;
import org.example.domain.company.Company;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.money.Currency;
import org.example.domain.money.Price;
import org.example.domain.product.Product;
import org.example.domain.product.ProductId;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DataCreator {
    public static Customer createCustomer1() {
        return new Customer(
                new CustomerId("AB2J-SG23"),
                "Kacper",
                "Piasecki",
                new NIP("1462693747"),
                createAddress1()
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
                new CustomerId("12HJ-S6F3"),
                "Kacper",
                "Maslany",
                new NIP("9527816928"),
                createAddress2()
        );
    }

    public static Customer createCustomer3() {
        return new Customer(
                new CustomerId("GL2C-6313"),
                "Karolina",
                "Koralowa",
                new NIP("0003768420"),
                createAddress3()

        );
    }


    public static Customer createCustomer4() {
        return new Customer(
                new CustomerId("1111-1111"),
                "Kacper",
                "Piasecki",
                new NIP("1462693747"),
                createAddress4()

        );
    }


    public static Customer createCustomer5() {
        return new Customer(
                new CustomerId("ABAB-5712"),
                "Wojtek",
                "Marciniak",
                new NIP("4627890357"),
                createAddress5()
        );
    }

    private static Address createAddress5() {
        return new Address(
                "Wroclaw",
                "Poland",
                "Polna 1",
                "87-297");
    }


    public static Invoice createInvoice1() {
        return new Invoice(
                new InvoiceId("AZG--421-A5"),
                createCustomer1(),
                createCompany1(),
                createProduct1(),
                OffsetDateTime.of(
                        2020, 12, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC)
        );
    }public static Invoice createInvoice2() {
        return new Invoice(
                new InvoiceId("ANP--444-A2"),
                createCustomer2(),
                createCompany2(),
                createProduct2(),
                OffsetDateTime.of(
                        2021, 12, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC)
        );
    }public static Invoice createInvoice3() {
        return new Invoice(
                new InvoiceId("PPP--001-A2"),
                createCustomer3(),
                createCompany3(),
                createProduct3(),
                OffsetDateTime.of(
                        2022, 12, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC)
        );
    }public static Invoice createInvoice4() {
        return new Invoice(
                new InvoiceId("LLL--777-00"),
                createCustomer4(),
                createCompany4(),
                createProduct4(),
                OffsetDateTime.of(
                        2023, 12, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC)
        );
    }public static Invoice createInvoice5() {
        return new Invoice(
                new InvoiceId("AZG--421-A5"),
                createCustomer5(),
                createCompany5(),
                createProduct5(),
                OffsetDateTime.of(
                        2024, 12, 12,
                        15, 17, 2, 4,
                        ZoneOffset.UTC)
        );
    }

    public static Product createProduct1() {
        return new Product(
                new ProductId("ABCDE48B"),
                "zabawka",
                new Price(new BigDecimal("123.23"), Currency.PLN)
        );
    }

    public static Product createProduct2() {
        return new Product(
                new ProductId("ABC76B8B"),
                "obiad",
                new Price(new BigDecimal("4211"), Currency.EUR)
        );
    }

    public static Product createProduct3() {
        return new Product(
                new ProductId("19BDE488"),
                "metal",
                new Price(new BigDecimal("1.25"), Currency.USD)
        );
    }

    public static Product createProduct4() {
        return new Product(
                new ProductId("111CD89M"),
                "ciastko",
                new Price(new BigDecimal("15000"), Currency.HUF)
        );
    }

    public static Product createProduct5() {
        return new Product(
                new ProductId("ABCDE48B"),
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
                createAddress1()
        );
    }

    public static Company createCompany3() {
        return new Company(
                "Samsung",
                new NIP("1223334490"),
                createAddress1()
        );
    }

    public static Company createCompany4() {
        return new Company(
                "Apple",
                new NIP("2900182654"),
                createAddress1()
        );
    }

    public static Company createCompany5() {
        return new Company(
                "Edifier",
                new NIP("2200997452"),
                createAddress1()
        );
    }


}
