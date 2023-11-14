package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.port.in.customer.FindCustomerUseCase;
import org.example.port.out.CustomerRepository;

import java.util.Optional;

@AllArgsConstructor

public class FindUserByNIPService implements FindCustomerUseCase {

    private CustomerRepository customerRepository;
    @Override
    public Optional<Customer> findUserByNIP(NIP nip) {
        return customerRepository.findCustomerByNIP(nip);
    }
}
