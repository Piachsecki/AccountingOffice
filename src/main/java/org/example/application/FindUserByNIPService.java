package org.example.application;

import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.port.in.customer.FIndCustomerUseCase;
import org.example.port.out.CustomerRepository;

import java.util.Optional;

public class FindUserByNIPService implements FIndCustomerUseCase {

    private CustomerRepository customerRepository;
    @Override
    public Optional<Customer> findUserByNIP(NIP nip) {
        return Optional.ofNullable(customerRepository.findCustomerByNIP(nip));
    }
}
