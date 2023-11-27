package org.example.adapter.out.database.entity;

import jakarta.persistence.*;
import org.example.domain.Address;
import org.example.domain.NIP;

@Table(name = "company")
@Entity
public class CompanyDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "company_name")
    private String companyName;
    @Embedded
    private NIP nip;

    @OneToOne
    private AddressDatabaseEntity addressDatabaseEntity;


}

