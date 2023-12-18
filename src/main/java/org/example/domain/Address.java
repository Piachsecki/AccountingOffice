package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Address {
    private String city;
    private String country;
    private String address;
    private String postalCode;

}
