package org.example.domain.customer;

import lombok.*;
import org.example.domain.Address;
import org.example.domain.NIP;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.port.in.customer.InsertInvoiceToCustomer;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode(exclude = {"name", "surname", "address"})
@With
@ToString
@Getter
public class Customer implements InsertInvoiceToCustomer {

    private CustomerId customerId;
    private String name;
    private String surname;
    private NIP nip;
    private Address address;
    private final OffsetDateTime joinDate;
    private final Entrepreneurship entrepreneurshipForm;
    private List<Invoice> incomeInvoices;
    private List<Invoice> costInvoices;


    @Override
    public void insertIncomeInvoiceToCustomer(IncomeInvoice invoice) {
        this.getIncomeInvoices().add(invoice);
    }

    @Override
    public void insertCostInvoiceToCustomer(CostInvoice invoice) {
        this.getCostInvoices().add(invoice);
    }
}
