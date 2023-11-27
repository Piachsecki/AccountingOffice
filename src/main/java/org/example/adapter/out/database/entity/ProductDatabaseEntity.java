package org.example.adapter.out.database.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.domain.money.Price;
import org.example.domain.product.ProductId;
@EqualsAndHashCode
@Entity
@ToString(of = {"productId", "productName", "price"})
@Table(name  = "product")
public class ProductDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private ProductId productId;

    @Column(name = "product_name")
    private String productName;

    @Embedded
    private Price price;

    @OneToOne
    private CostInvoiceDatabaseEntity costInvoiceDatabaseEntity;
}
