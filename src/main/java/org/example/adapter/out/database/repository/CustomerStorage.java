package org.example.adapter.out.database.repository;

import org.example.adapter.out.database.configuration.DatabaseHibernateConfig;
import org.example.adapter.out.database.entity.AddressDatabaseEntity;
import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.example.domain.Address;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.port.out.CustomerRepository;
import org.hibernate.Session;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CustomerStorage implements CustomerRepository {
    @Override
    public Customer addCustomer(Customer customer) {
        try(Session session = DatabaseHibernateConfig.getSession()){
            if(Objects.isNull(session)){
                throw new RuntimeException();
            }
            session.beginTransaction();
            Address address = customer.getAddress();
            AddressDatabaseEntity addressDatabaseEntity = AddressDatabaseEntity
                    .builder()
                    .city(address.getCity())
                    .country(address.getCountry())
                    .address(address.getAddress())
                    .postalCode(address.getPostalCode())
                    .build();

            CustomerDatabaseEntity customerDatabaseEntity = CustomerDatabaseEntity
                    .builder()
                    .name(customer.getName())
                    .surname(customer.getSurname())
                    .joinDate(customer.getJoinDate())
                    .entrepreneurshipForm(customer.getEntrepreneurshipForm().entrepreneurshipForm().toString())
                    .nip(customer.getNip().value())
                    .taxPaymentForm(customer.getEntrepreneurshipForm().taxPaymentForm().toString())
                    .taxRate(customer.getEntrepreneurshipForm().taxPaymentForm().getTaxRate().getValue())
                    .address(addressDatabaseEntity)
                    .build();
            session.persist(customerDatabaseEntity);
            session.getTransaction().commit();
            return customer;
        }
    }

    @Override
    public void deleteCustomer(CustomerId customerId) {
        try(Session session = DatabaseHibernateConfig.getSession()){
            if(Objects.isNull(session)){
                throw new RuntimeException();
            }

            UUID customerIdAsUUID = customerId.getCustomerIdAsUUID();
//            System.out.println("customerIdAsUUID: " + customerIdAsUUID);
            session.beginTransaction();
            session.remove(session.find(CustomerDatabaseEntity.class, customerIdAsUUID));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateCustomer(CustomerId customerId) {

    }

    @Override
    public void deleteAllCustomers() {
        try(Session session = DatabaseHibernateConfig.getSession()){
            if(Objects.isNull(session)){
                throw new RuntimeException();
            }
            session.beginTransaction();
            session.createMutationQuery("DELETE from CustomerDatabaseEntity").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<Customer> findCustomerByNIP(NIP nip) {
        try(Session session = DatabaseHibernateConfig.getSession()){
            if(Objects.isNull(session)){
                throw new RuntimeException("session is null");
            }
            Optional<CustomerDatabaseEntity> customer = Optional.ofNullable(session.find(CustomerDatabaseEntity.class,nip));



                return Optional.ofNullable(new Customer());
        }
    }
}
