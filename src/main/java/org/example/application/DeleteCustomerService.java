package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.customer.CustomerId;
import org.example.port.in.customer.DeleteCustomerUseCase;
import org.example.port.out.CustomerRepository;

@AllArgsConstructor

public class DeleteCustomerService implements DeleteCustomerUseCase {
    private CustomerRepository customerRepository;
    @Override
    public void deleteCustomer(CustomerId customerId) {
        customerRepository.deleteCustomer(customerId);
    }
}
