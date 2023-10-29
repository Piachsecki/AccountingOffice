package org.example.adapter.out;

import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.port.out.CustomerRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCustomerRepo implements CustomerRepository {
    Map<CustomerId, Customer> customers = new HashMap<>();

    @Override
    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    @Override
    public void deleteCustomer(CustomerId customerId) {
        customers.remove(customerId);
    }

    @Override
    public void updateCustomer(CustomerId customerId) {

    }

    @Override
    public void deleteAllCustomers() {
        for (CustomerId customerId : customers.keySet()) {
            customers.remove(customerId);
        }
    }
}
