package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.customer.CustomerId;
import org.example.port.in.customer.DeleteCustomerUseCase;
import org.example.port.in.invoice.DeleteInvoiceUseCase;
import org.example.port.out.CustomerRepository;

@AllArgsConstructor

public class DeleteCustomerService implements DeleteCustomerUseCase {
    private CustomerRepository customerRepository;
    private DeleteInvoiceUseCase deleteInvoiceUseCase;
    @Override
    public void deleteCustomer(CustomerId customerId) {
        deleteInvoiceUseCase.deleteAllWithUser(customerId);
        customerRepository.deleteCustomer(customerId);
    }
}
