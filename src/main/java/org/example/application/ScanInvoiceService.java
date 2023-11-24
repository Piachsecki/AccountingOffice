package org.example.application;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.example.domain.invoice.IncomeInvoice;
import org.example.domain.invoice.Invoice;
import org.example.port.in.invoice.ScanInvoiceUseCase;
import org.example.port.out.InvoiceRepository;

import java.io.File;

@Slf4j
public class ScanInvoiceService implements ScanInvoiceUseCase {
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice scanInvoice(String filePath) {
        try {
            File image = new File(filePath);
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/tessdata");
            tesseract.setLanguage("eng");
            tesseract.setPageSegMode(1);
            tesseract.setOcrEngineMode(1);
            String result = tesseract.doOCR(image);
//            Invoice invoice = new IncomeInvoice();
//            invoiceRepository.insertInvoice(invoice);
//            return invoice;
            return null;
        } catch (TesseractException e) {
            log.error("");
            throw new RuntimeException(e.getMessage());
        }

    }
}
