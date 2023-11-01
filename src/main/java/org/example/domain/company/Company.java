package org.example.domain.company;

import lombok.AllArgsConstructor;
import org.example.domain.Address;
import org.example.domain.NIP;

@AllArgsConstructor
public class Company {
    private String companyName;
    private NIP nip;
    private Address address;

}
