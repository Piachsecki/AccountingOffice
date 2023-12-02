package org.example.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.exceptions.CustomerIdException;

import java.util.Objects;
import java.util.UUID;

@Slf4j
public record CustomerId(String value) {
    public CustomerId {
        if (Objects.isNull(value) || value.isEmpty()){
            log.error("Given id {} cannot be used as CustomerId", value);
            throw new CustomerIdException(
                    String.format("Given value:[%s] cannot be used as CustomerId!", value));
        }
    }

    public static CustomerId createRandomCustomerId(){
        UUID uuid = UUID.randomUUID();
        return new CustomerId(uuid.toString());
    }

    @Override
    public String toString() {
        return value;
    }

    public UUID getCustomerIdAsUUID(){
        return UUID.fromString(this.value);
    }
}
