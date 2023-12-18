package org.example.domain.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.Address;
import org.example.domain.NIP;

@AllArgsConstructor
@Getter
public class Company {
    private String companyName;
    private NIP nip;
    private Address address;

}
