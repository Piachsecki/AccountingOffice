package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.entity.ProductDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.ProductMapper;
import org.example.domain.product.Product;

public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product productEntityToProductDomainMapper(ProductDatabaseEntity productDatabaseEntity) {
        return null;
    }

    @Override
    public ProductDatabaseEntity productDomainToProductDatabaseEntityMapper(Product productDomain) {
        return null;
    }
}
