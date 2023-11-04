package org.example.adapter.out;

import lombok.Getter;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.port.out.CustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class InMemoryCustomerRepo implements CustomerRepository {
    private final Map<CustomerId, Customer> customers = new HashMap<>();


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
            customers.clear();
    }

    @Override
    public Optional<Customer> findCustomerByNIP(NIP nip) {
        for (Map.Entry<CustomerId, Customer> customerIdCustomerEntry : customers.entrySet()) {
            if(customerIdCustomerEntry.getValue().getNip().equals(nip)){
                return Optional.of(customerIdCustomerEntry.getValue());
            }
        }
        return Optional.empty();
    }
}
