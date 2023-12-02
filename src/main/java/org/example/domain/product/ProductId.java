package org.example.domain.product;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.exceptions.ProductIdException;

import java.util.Objects;
import java.util.UUID;

@Slf4j
public record ProductId(String value) {
    public ProductId {
        if (Objects.isNull(value) || value.isEmpty()){
            log.error("Given value: {} cannot be used as ProductId!", value);
            throw new ProductIdException(
                    String.format("Given value:[%s] cannot be used as ProductId!", value));
        }
    }

    public static ProductId createRandomProductId(){
        UUID uuid = UUID.randomUUID();
        return new ProductId(uuid.toString());
    }
}
