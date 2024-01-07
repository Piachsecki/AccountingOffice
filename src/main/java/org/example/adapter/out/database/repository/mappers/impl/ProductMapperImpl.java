package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.entity.ProductDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.ProductMapper;
import org.example.domain.money.Price;
import org.example.domain.product.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product productEntityToProductDomainMapper(ProductDatabaseEntity productDatabaseEntity) {
        return new Product(
                productDatabaseEntity.getProductId(),
                productDatabaseEntity.getProductName(),
                new Price(productDatabaseEntity.getAmount(), org.example.domain.money.Currency.valueOf(productDatabaseEntity.getCurrency()))
        );
    }

    @Override
    public ProductDatabaseEntity productDomainToProductDatabaseEntityMapper(Product productDomain) {
        return ProductDatabaseEntity.builder()
                .productName(productDomain.getProductName())
                .amount(productDomain.getPrice().amount())
                .currency(productDomain.getPrice().currency().toString())
                .build();
    }
}
