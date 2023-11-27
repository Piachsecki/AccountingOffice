package org.example.adapter.out.database.repository;

import org.example.adapter.out.database.configuration.HibernateUtil;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.port.out.CustomerRepository;
import org.hibernate.Session;

import java.util.Objects;
import java.util.Optional;

public class CustomerStorage implements CustomerRepository {
    @Override
    public void addCustomer(Customer customer) {
        try(Session session = HibernateUtil.getSession()){
            if(Objects.isNull(session)){
                throw new RuntimeException();
            }
            session.beginTransaction();
            session.persist(customer);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteCustomer(CustomerId customerId) {
        try(Session session = HibernateUtil.getSession()){
            if(Objects.isNull(session)){
                throw new RuntimeException();
            }
            session.beginTransaction();
            String query = "DELETE FROM CustomerDatabaseEntity cust WHERE cust"
            session.remove(session.find(Customer.class, customerId));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateCustomer(CustomerId customerId) {

    }

    @Override
    public void deleteAllCustomers() {
        try(Session session = HibernateUtil.getSession()){
            if(Objects.isNull(session)){
                throw new RuntimeException();
            }
            session.beginTransaction();
            String query = "SELECT FROM CustomerDatabaseEntity cust";
            session.createQuery(query, Customer.class).list().forEach(session::remove);
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<Customer> findCustomerByNIP(NIP nip) {
        try(Session session = HibernateUtil.getSession()){
            if(Objects.isNull(session)){
                throw new RuntimeException("session is null");
            }
            Optional<Customer> customer = Optional.ofNullable(session.find(Customer.class,nip));
            return customer;
        }
    }
}
