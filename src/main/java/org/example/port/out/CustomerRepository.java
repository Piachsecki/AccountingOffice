package org.example.port.out;

import org.example.domain.NIP;
import org.example.domain.customer.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer addCustomer(Customer customer);

    void deleteCustomer(UUID customerId);
    void deleteAllCustomers();

    Optional <Customer> findCustomerByNIP(NIP nip);

}
