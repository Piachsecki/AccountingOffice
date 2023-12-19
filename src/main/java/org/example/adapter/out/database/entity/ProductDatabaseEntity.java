package org.example.adapter.out.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "productId")
@Entity
@ToString(of = {"productId", "productName", "amount", "currency"})
@Table(name = "product")
public class ProductDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "currency")
    private String currency;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "product")
    private CostInvoiceDatabaseEntity costInvoice;
}
