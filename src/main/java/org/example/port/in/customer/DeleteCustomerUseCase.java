package org.example.port.in.customer;


import java.util.UUID;

public interface DeleteCustomerUseCase {
    void deleteCustomer(UUID customerId);

    void deleteAll();
}
