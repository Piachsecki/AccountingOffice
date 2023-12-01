package org.example.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.money.Price;

@AllArgsConstructor
@Getter
public class Product {
    private ProductId productId;
    private String productName;
    private Price price;

}
