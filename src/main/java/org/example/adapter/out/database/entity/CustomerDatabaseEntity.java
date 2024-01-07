package org.example.adapter.out.database.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "customerId")
@Entity
@ToString(of = {"customerId", "name", "surname"})
@Table(name = "customer")
public class CustomerDatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "join_date")
    private OffsetDateTime joinDate;

    @Column(name = "entrepreneurship_form")
    private String entrepreneurshipForm;

    @Column(name = "nip")
    private String nip;

    @Column(name = "tax_payment_form")
    private String taxPaymentForm;

    @Column(name = "tax_rate")
    private String taxRate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CostInvoiceDatabaseEntity> costInvoices;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<IncomeInvoiceDatabaseEntity> incomeInvoices;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", unique = true)
    private AddressDatabaseEntity address;


}
