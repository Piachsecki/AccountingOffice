package org.example.adapter.out.database.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.adapter.out.database.repository.jpa.CustomerJpaRepository;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.port.out.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Slf4j
@Repository
@AllArgsConstructor
public class CustomerDatabaseStorage implements CustomerRepository {
    private CustomerJpaRepository customerJpaRepository;

    @Override
    public Customer addCustomer(Customer customer) {

//        customerJpaRepository.saveAndFlush();
        return null;


    }


    @Override
    public void deleteCustomer(UUID customerId) {

    }

    @Override
    public void deleteAllCustomers() {
        customerJpaRepository.deleteAll();
    }

    @Override
    public Optional<Customer> findCustomerByNIP(NIP nip) {
        return null;
    }


}

