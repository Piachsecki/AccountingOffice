package org.example.adapter.out.database.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.invoice.Invoice;
import org.example.port.out.InvoiceRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;


@Slf4j
@Repository
public class InvoiceDatabaseStorage implements InvoiceRepository {
    @Override
    public Invoice insertInvoice(UUID customerId, Invoice invoice) {

        log.error("Cannot return mapped IncomeInvoice/CostInvoice from database correctly");
        throw new RuntimeException("Cannot return mapped IncomeInvoice/CostInvoice from database correctly");
    }

    @Override
    public HashSet<Invoice> listAllInvoicesForCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public void deleteCostInvoiceForCustomerId(UUID customerId, UUID invoiceId) {

    }

    @Override
    public void deleteIncomeInvoiceForCustomerId(UUID customerId, UUID invoiceId) {

    }


    @Override
    public List<Invoice> listCostInvoices(UUID customerId) {
        return null;
    }

    @Override
    public List<Invoice> listIncomeInvoices(UUID customerId) {
        return null;
    }


}
