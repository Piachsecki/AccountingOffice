package org.example.adapter;

import org.example.domain.Customer;
import org.example.domain.Product;
import org.example.port.driven.ForStoringCustomer;
import org.example.port.driven.ForStoringProduct;

import java.util.Map;

public class InMemoryDB implements ForStoringProduct, ForStoringCustomer {
    Map<String, Product> db;

    @Override
    public Product findProductById(String id) {
        return null;
    }

    @Override
    public Customer findById(String id) {
        return null;
    }
}
