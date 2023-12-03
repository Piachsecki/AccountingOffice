package org.example.adapter.out.maps;

import lombok.Getter;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.port.out.CustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
public class InMemoryCustomerRepo implements CustomerRepository {
    private final Map<UUID, Customer> customers = new HashMap<>();

    @Override
    public Customer addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        customers.remove(customerId);
    }

    @Override
    public void deleteAllCustomers() {
            customers.clear();
    }

    @Override
    public Optional<Customer> findCustomerByNIP(NIP nip) {
        for (Map.Entry<UUID, Customer> customerIdCustomerEntry : customers.entrySet()) {
            if(customerIdCustomerEntry.getValue().getNip().equals(nip)){
                return Optional.of(customerIdCustomerEntry.getValue());
            }
        }
        return Optional.empty();
    }

}
