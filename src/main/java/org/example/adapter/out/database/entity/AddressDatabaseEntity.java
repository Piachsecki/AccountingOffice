package org.example.adapter.out.database.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "addressId")
@Entity
@Table(name = "address")
public class AddressDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    private UUID addressId;

    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "address")
    private String address;
    @Column(name = "postal_code")
    private String postalCode;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private CompanyDatabaseEntity company;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private CustomerDatabaseEntity customer;
}
