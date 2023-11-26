package org.example.domain.invoice;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.exceptions.InvoiceIdException;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Slf4j
public record InvoiceId(String value) {
    public InvoiceId {
        if (Objects.isNull(value) || value.isEmpty()) {
            log.error("Given value: {} cannot be used as InvoiceId", value);
            throw new InvoiceIdException(
                    String.format("Given value:[%s] cannot be used as InvoiceId" +
                            "!", value));
        }
    }

    public static InvoiceId createRandomInvoiceId() {
        UUID uuid = UUID.randomUUID();
        return new InvoiceId(uuid.toString());
    }
}
