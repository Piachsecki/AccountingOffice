package org.example.adapter.out.database.entity;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.domain.NIP;
import org.example.domain.customer.EntrepreneurshipForm;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;


@EqualsAndHashCode
@Entity
@ToString(of = {"customerId", "name", "surname"})
@Table(name  = "customer")
public class CustomerDatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id")
    private UUID customerId;

    @Embedded
    private EntrepreneurshipForm entrepreneurshipForm;

    @Embedded
    private NIP nip;

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;

    @Column(name = "join_date")
    private OffsetDateTime joinDate;


    @OneToMany
    private Set<CostInvoiceDatabaseEntity> costInvoices;

    @OneToMany
    private Set<IncomeInvoiceDatabaseEntity> costInvoices;

    @OneToOne
    private AddressDatabaseEntity address;


}
