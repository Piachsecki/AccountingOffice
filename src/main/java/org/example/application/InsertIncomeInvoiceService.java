package org.example.application;

import lombok.RequiredArgsConstructor;
import org.example.domain.customer.Customer;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.domain.money.Money;
import org.example.port.in.invoice.InsertIncomeInvoiceUseCase;
import org.example.port.out.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InsertIncomeInvoiceService implements InsertIncomeInvoiceUseCase {
    private final InvoiceRepository invoiceRepository;

@Transactional
    @Override
    public Invoice insertIncomeInvoice(
            Customer customer,
            OffsetDateTime date,
            Money money
    ) {
        IncomeInvoice invoice = new IncomeInvoice(
                UUID.randomUUID(),
                customer,
                date,
                money
        );
        customer.insertIncomeInvoiceToCustomer(invoice);
        invoiceRepository.insertInvoice(customer.getCustomerId(), invoice);

        return invoice;
    }

    @Override
    public Invoice insertIncomeInvoice(UUID customerId, IncomeInvoice invoice) {
//        invoice.getCustomer().getIncomeInvoices().add(invoice);
        invoiceRepository.insertInvoice(customerId, invoice);
        return invoice;
    }

}
