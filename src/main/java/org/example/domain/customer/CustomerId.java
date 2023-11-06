package org.example.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.exceptions.CustomerIdException;

import java.util.Objects;
import java.util.Random;

@Slf4j
public record CustomerId(String value) {
    private  static final String CHARS = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private  static final int LENGTH = 8;

    public CustomerId {
        if (Objects.isNull(value) || value.length() > LENGTH + 1){
            log.error("Given id {} cannot be used as CustomerId", value);
            throw new CustomerIdException(
                    String.format("Given value:[%s] cannot be used as CustomerId!", value));
        }
    }

    public static CustomerId createRandomCustomerId(){
        Random random = new Random();
        var charTable = new char[LENGTH];
        StringBuilder ourId = new StringBuilder();
        for (int i =0; i < charTable.length; i++) {
            charTable[i] = CHARS.charAt(random.nextInt(CHARS.length()));
            ourId.append(charTable[i]);
            if (i > LENGTH-4){
                ourId.append("-");
            }
        }


        return new CustomerId(ourId.toString());
    }
}
