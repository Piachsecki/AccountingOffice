package org.example.application;

import org.example.domain.customer.CustomerId;
import org.example.port.in.customer.DeleteCustomerUseCase;
import org.example.port.out.CustomerRepository;

public class DeleteCustomerService implements DeleteCustomerUseCase {
    private CustomerRepository customerRepository;
    private DeleteInvoiceService deleteInvoiceService;
    @Override
    public void deleteCustomer(CustomerId customerId) {
        deleteInvoiceService.deleteAll(customerId);
        customerRepository.deleteCustomer(customerId);
    }
}
