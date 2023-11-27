package org.example.adapter.out.database.entity;


import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name  = "address")
public class AddressDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "address")
    private String address;
    @Column(name = "postal_code")
    private String postalCode;


    @OneToOne
    private CompanyDatabaseEntity companyDatabaseEntity;
    @OneToOne
    private CustomerDatabaseEntity customerDatabaseEntity;
}
