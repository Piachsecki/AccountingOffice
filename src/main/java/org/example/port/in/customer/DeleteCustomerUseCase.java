package org.example.port.in.customer;

import org.example.domain.customer.CustomerId;

public interface DeleteCustomerUseCase {
    void deleteCustomer(CustomerId customerId);
}
