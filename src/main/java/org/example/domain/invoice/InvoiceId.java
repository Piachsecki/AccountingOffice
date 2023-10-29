package org.example.domain.invoice;

import org.example.domain.exceptions.InvoiceIdException;

import java.util.Objects;
import java.util.Random;

public record InvoiceId(String value) {
    private static final String NUMS = "0123456789";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LENGTH = 8;

    public InvoiceId {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new InvoiceIdException(
                    String.format("Given value:[%s] cannot be used as InvoiceId" +
                            "!", value));
        }
    }

    public static InvoiceId createRandomInvoiceId
            () {
        Random random = new Random();
        var charTable = new char[LENGTH];

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            if (i < 3){
                result.append(ALPHABET.charAt(random.nextInt(ALPHABET.length()))).append("--");
            }
            else if (i < 5){
                result.append(NUMS.charAt(random.nextInt(NUMS.length()))).append("-");
            }
            else {
                result.append(ALL.charAt(random.nextInt(ALL.length())));
            }

        }
        return new InvoiceId(result.toString());
    }
}
