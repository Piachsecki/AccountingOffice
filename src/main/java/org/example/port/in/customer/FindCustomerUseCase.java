package org.example.port.in.customer;

import org.example.domain.NIP;
import org.example.domain.customer.Customer;

public interface FindCustomerUseCase {
    Customer findUserByNIP(NIP nip);
}
