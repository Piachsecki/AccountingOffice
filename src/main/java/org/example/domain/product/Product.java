package org.example.domain.product;

import lombok.AllArgsConstructor;
import org.example.domain.money.Price;

@AllArgsConstructor
public class Product {
    private ProductId productId;
    private String productName;
    private Price price;

}
