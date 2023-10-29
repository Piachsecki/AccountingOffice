package org.example.application;

import org.example.port.driven.ForStoringProduct;
import org.example.port.driver.ForInvoicing;

public class InvoicingService implements ForInvoicing {


    private final ForStoringProduct forStoringProduct;

    public InvoicingService(ForStoringProduct forStoringProduct) {
        this.forStoringProduct = forStoringProduct;
    }

    @Override
    public void createInvoice() {

    }
}
