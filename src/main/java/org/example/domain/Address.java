package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Address {
    private String city;
    private String country;
    private String address;
    private String postalCode;

}
