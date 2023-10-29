package org.example.port.driven;

import org.example.domain.Product;

public interface ForStoringProduct {

    Product findProductById(String id);
}
