package org.example.application;

import lombok.AllArgsConstructor;
import org.example.port.in.customer.DeleteCustomerUseCase;
import org.example.port.out.CustomerRepository;

import java.util.UUID;

@AllArgsConstructor

public class DeleteCustomerService implements DeleteCustomerUseCase {
    private CustomerRepository customerRepository;
    @Override
    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteCustomer(customerId);
    }
}
