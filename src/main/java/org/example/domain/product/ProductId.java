package org.example.domain.product;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.exceptions.ProductIdException;

import java.util.Objects;
import java.util.Random;

@Slf4j
public record ProductId(String value) {
    private  static final String CHARS = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private  static final int LENGTH = 8;

    public ProductId {
        if (Objects.isNull(value) || value.isEmpty()){
            log.error("Given value: {} cannot be used as ProductId!", value);
            throw new ProductIdException(
                    String.format("Given value:[%s] cannot be used as ProductId!", value));
        }
    }

    public static ProductId createRandomProductId(){
        Random random = new Random();
        var charTable = new char[LENGTH];
        for (int i =0; i < charTable.length; i++) {
            charTable[i] = CHARS.charAt(random.nextInt(CHARS.length()));
        }

        return new ProductId(new String(charTable));
    }
}
