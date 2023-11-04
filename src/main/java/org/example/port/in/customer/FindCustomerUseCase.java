package org.example.port.in.customer;

import org.example.domain.NIP;
import org.example.domain.customer.Customer;

import java.util.Optional;

public interface FindCustomerUseCase {
    Optional<Customer> findUserByNIP(NIP nip);
}
