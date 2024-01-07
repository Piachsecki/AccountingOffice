package org.example.adapter.out.database.repository.mappers;

import org.example.adapter.out.database.entity.ProductDatabaseEntity;
import org.example.domain.product.Product;

public interface ProductMapper {
    Product productEntityToProductDomainMapper(ProductDatabaseEntity productDatabaseEntity);

    ProductDatabaseEntity productDomainToProductDatabaseEntityMapper(Product productDomain);
}
