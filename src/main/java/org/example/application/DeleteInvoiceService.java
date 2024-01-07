package org.example.application;

import lombok.AllArgsConstructor;
import org.example.port.in.invoice.DeleteInvoiceUseCase;
import org.example.port.out.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service

public class DeleteInvoiceService implements DeleteInvoiceUseCase {
    private InvoiceRepository invoiceRepository;

    @Override
    public void deleteInvoice(UUID customerId, UUID invoiceId) {
        invoiceRepository.deleteCostInvoiceForCustomerId(customerId, invoiceId);
    }


}
