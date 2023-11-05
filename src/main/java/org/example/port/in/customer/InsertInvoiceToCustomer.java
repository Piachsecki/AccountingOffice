package org.example.port.in.customer;

import org.example.domain.invoice.CostInvoice;
import org.example.domain.invoice.IncomeInvoice;

public interface InsertInvoiceToCustomer {

    void insertIncomeInvoiceToCustomer(IncomeInvoice invoice);

    void insertCostInvoiceToCustomer(CostInvoice invoice);
}
