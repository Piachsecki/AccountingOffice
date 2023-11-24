package org.example.port.in.invoice;

import net.sourceforge.tess4j.TesseractException;
import org.example.domain.invoice.Invoice;

public interface ScanInvoiceUseCase {
    Invoice scanInvoice(String filePath) throws TesseractException;
}
