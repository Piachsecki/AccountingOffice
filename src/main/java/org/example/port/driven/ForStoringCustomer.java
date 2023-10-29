package org.example.port.driven;

import org.example.domain.Customer;

public interface ForStoringCustomer {

    Customer findById(String id);
}
