package org.example.port.out;

import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;

import java.util.Optional;

public interface CustomerRepository {
    void addCustomer(Customer customer);

    void deleteCustomer(CustomerId customerId);

    void updateCustomer(CustomerId customerId);

    void deleteAllCustomers();

    Optional <Customer> findCustomerByNIP(NIP nip);

}
