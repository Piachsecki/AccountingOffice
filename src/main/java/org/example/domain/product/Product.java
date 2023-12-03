package org.example.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.money.Price;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Product {
    private UUID productId;
    private String productName;
    private Price price;

}
