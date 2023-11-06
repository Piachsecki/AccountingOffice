package org.example.application;

import org.example.domain.customer.Customer;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.money.Income;
import org.example.port.in.invoice.InsertIncomeInvoiceUseCase;
import org.example.port.out.InvoiceRepository;

import java.time.OffsetDateTime;

public class InsertIncomeInvoiceService implements InsertIncomeInvoiceUseCase {
        private InvoiceRepository invoiceRepository;
        @Override
        public Invoice insertIncomeInvoice(
                Customer customer,
                OffsetDateTime date,
                Income income
        ) {
            IncomeInvoice invoice = new IncomeInvoice(
                    InvoiceId.createRandomInvoiceId(),
                    customer,
                    date,
                    income
            );
            customer.insertIncomeInvoiceToCustomer(invoice);
            invoiceRepository.insertIncomeInvoice(invoice);

            return invoice;
        }

    @Override
    public Invoice insertIncomeInvoice(IncomeInvoice invoice) {
        invoice.getCustomer().insertIncomeInvoiceToCustomer(invoice);
        invoiceRepository.insertCostInvoice(invoice);

        return invoice;
    }

}
