package org.example.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.example.domain.Address;
import org.example.domain.NIP;

@AllArgsConstructor
@With
public class Customer {
    @Getter
    private CustomerId customerId;
    private String name;
    private String surname;
    @Getter
    private NIP nip;
    private Address address;

}
