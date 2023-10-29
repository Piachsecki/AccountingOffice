package org.example.domain.customer;

import lombok.Getter;
import org.example.domain.Address;
import org.example.domain.NIP;


public class Customer {
    @Getter
    private CustomerId customerId;
    private String name;
    private String surname;
    private NIP nip;
    private Address address;

}
