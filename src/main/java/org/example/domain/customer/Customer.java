package org.example.domain.customer;

import lombok.*;
import org.example.domain.Address;
import org.example.domain.NIP;

@AllArgsConstructor
@EqualsAndHashCode(exclude = {"name", "surname", "address"})
@With
@ToString
public class Customer {
    @Getter

    private CustomerId customerId;
    private String name;
    private String surname;
    @Getter
    private NIP nip;
    private Address address;

}
