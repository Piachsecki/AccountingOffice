package org.example.application;

import lombok.RequiredArgsConstructor;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.port.in.customer.AddCustomerUseCase;
import org.example.port.in.customer.DeleteCustomerUseCase;
import org.example.port.in.customer.FindCustomerUseCase;
import org.example.port.out.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService implements AddCustomerUseCase, DeleteCustomerUseCase, FindCustomerUseCase {
    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.addCustomer(customer);
    }
    @Transactional
    @Override
    public void deleteAll() {
        customerRepository.deleteAllCustomers();
    }

    @Transactional
    @Override
    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteCustomer(customerId);
    }
    @Transactional
    @Override
    public Customer findUserByNIP(NIP nip) {
        Optional<Customer> customerByNIP = customerRepository.findCustomerByNIP(nip);
        if (customerByNIP.isEmpty()) {
            throw new RuntimeException(String.format("There is no user with the given NIP[%s]", nip));
        }
        return customerByNIP.get();
    }
}
