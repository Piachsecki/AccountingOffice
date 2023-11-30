package org.example.adapter.out.database.repository;

import org.example.adapter.out.database.configuration.DatabaseHibernateConfig;
import org.example.domain.customer.CustomerId;
import org.example.domain.invoice.Invoice;
import org.example.domain.invoice.InvoiceId;
import org.example.domain.money.Money;
import org.example.port.out.InvoiceRepository;
import org.hibernate.Session;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Objects;

public class InvoiceRepositoryImpl implements InvoiceRepository {
    @Override
    public void insertInvoice(Invoice invoice) {
        try(Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException();
            }
            session.beginTransaction();
//            invoice.getCustomer().getNip();


        }
    }

    @Override
    public HashSet<Invoice> listAllInvoicesForCustomerId(CustomerId customerId) {
        return null;
    }

    @Override
    public void deleteInvoiceForCustomerId(CustomerId customerId, InvoiceId invoiceId) {

    }

    @Override
    public void deleteAllInvoicesForCustomerId(CustomerId customerId) {

    }

    @Override
    public void deleteAllWithCustomer(CustomerId customerId) {

    }

}
