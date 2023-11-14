package org.example.domain.customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.domain.Address;
import org.example.domain.NIP;
import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.port.in.customer.InsertInvoiceToCustomer;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Customer implements InsertInvoiceToCustomer {
    @EqualsAndHashCode.Include

    private CustomerId customerId;
    private String name;
    private String surname;
    private NIP nip;
    private Address address;
    private final OffsetDateTime joinDate;
    private Entrepreneurship entrepreneurshipForm;
    private List<Invoice> incomeInvoices;
    private List<Invoice> costInvoices;


    @Override
    public void insertIncomeInvoiceToCustomer(IncomeInvoice invoice) {
    incomeInvoices.add(invoice);

    }

    @Override
    public void insertCostInvoiceToCustomer(CostInvoice invoice) {
        costInvoices.add(invoice);
    }

    public Customer withEntrepreneurshipForm(Entrepreneurship entrepreneurshipForm) {
        this.entrepreneurshipForm = entrepreneurshipForm;
        return this;

    }

}
