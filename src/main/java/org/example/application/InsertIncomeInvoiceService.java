package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.money.Money;
import org.example.port.in.invoice.InsertIncomeInvoiceUseCase;
import org.example.port.out.InvoiceRepository;

import java.time.OffsetDateTime;

@AllArgsConstructor

public class InsertIncomeInvoiceService implements InsertIncomeInvoiceUseCase {
        private InvoiceRepository invoiceRepository;
        @Override
        public Invoice insertIncomeInvoice(
                Customer customer,
                OffsetDateTime date,
                Money money
        ) {
            IncomeInvoice invoice = new IncomeInvoice(
                    InvoiceId.createRandomInvoiceId(),
                    customer,
                    date,
                    money
            );
            customer.insertIncomeInvoiceToCustomer(invoice);
            invoiceRepository.insertInvoice(customer.getCustomerId(), invoice);

            return invoice;
        }

    @Override
    public Invoice insertIncomeInvoice(IncomeInvoice invoice) {
        invoice.getCustomer().insertIncomeInvoiceToCustomer(invoice);
        invoiceRepository.insertInvoice(invoice.getCustomer().getCustomerId(), invoice);

        return invoice;
    }

}
