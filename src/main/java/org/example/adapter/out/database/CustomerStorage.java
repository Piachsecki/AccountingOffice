package org.example.adapter.out.database;

import org.example.adapter.out.database.configuration.HibernateUtil;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.port.out.CustomerRepository;

import java.util.Objects;
import java.util.Optional;
/*
Te repo bedzie mialao repozytorium ghibernatowe


do tej klasy masz to wstrzyknac i wtych klasach to uzywac, w tych metodach

 */


public class CustomerStorage implements CustomerRepository {
    @Override
    public void addCustomer(Customer customer) {
//        try(Session session = HibernateUtil.getSession()){
//            if(Objects.isNull(session)){
//                throw new RuntimeException();
//            }
//            session.beginTransaction();
//            session.persist(customer);
//            session.getTransaction().commit();
//        }
    }

    @Override
    public void deleteCustomer(CustomerId customerId) {

    }

    @Override
    public void updateCustomer(CustomerId customerId) {

    }

    @Override
    public void deleteAllCustomers() {

    }

    @Override
    public Optional<Customer> findCustomerByNIP(NIP nip) {
        return Optional.empty();
    }
}
