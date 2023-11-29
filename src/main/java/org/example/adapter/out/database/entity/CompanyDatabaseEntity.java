package org.example.adapter.out.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.Address;
import org.example.domain.NIP;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "companyId")
@Table(name = "company")
@Entity
public class CompanyDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "company_name")
    private String companyName;
    @Column(name = "nip")
    private String nip;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressDatabaseEntity address;


}

