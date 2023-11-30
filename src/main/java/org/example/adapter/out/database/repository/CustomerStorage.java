package org.example.adapter.out.database.repository;

import org.example.adapter.out.database.configuration.DatabaseHibernateConfig;
import org.example.adapter.out.database.entity.AddressDatabaseEntity;
import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.example.domain.Address;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.domain.customer.CustomerId;
import org.example.domain.customer.Entrepreneurship;
import org.example.domain.customer.EntrepreneurshipForm;
import org.example.domain.customer.TaxPayments.*;
import org.example.port.out.CustomerRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CustomerStorage implements CustomerRepository {
    @Override
    public Customer addCustomer(Customer customer) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
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
            customer.setCustomerId(new CustomerId(customerDatabaseEntity.getCustomerId().toString()));
            System.out.println(customer);
            return customer;
        }
    }

    @Override
    public void deleteCustomer(CustomerId customerId) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException();
            }
            UUID customerIdAsUUID = customerId.getCustomerIdAsUUID();
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
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException();
            }
            session.beginTransaction();
            session.createMutationQuery("DELETE from CustomerDatabaseEntity").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<Customer> findCustomerByNIP(NIP nip) {
        try (Session session = DatabaseHibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("session is null");
            }
            session.beginTransaction();
            String query = "SELECT cust FROM CustomerDatabaseEntity cust WHERE cust.nip = :nip";
            CustomerDatabaseEntity customerDatabaseEntity = session.createQuery(query, CustomerDatabaseEntity.class)
                    .setParameter("nip", nip.toString())
                    .uniqueResult();
            session.getTransaction().commit();

            AddressDatabaseEntity addressDatabaseEntity = customerDatabaseEntity.getAddress();
            Address address = Address.builder()
                    .city(addressDatabaseEntity.getCity())
                    .country(addressDatabaseEntity.getCountry())
                    .address(addressDatabaseEntity.getAddress())
                    .postalCode(addressDatabaseEntity.getPostalCode())
                    .build();


            Entrepreneurship entrepreneurship = mapEntrepreneurshipFromCustomerDatabaseEntity(customerDatabaseEntity);

            Customer customer = Customer.builder()
                    .customerId(new CustomerId(customerDatabaseEntity.getCustomerId().toString()))
                    .name(customerDatabaseEntity.getName())
                    .surname(customerDatabaseEntity.getSurname())
                    .nip(nip)
                    .address(address)
                    .joinDate(customerDatabaseEntity.getJoinDate())
                    .entrepreneurshipForm(entrepreneurship)
                    .build();

            return Optional.of(customer);
        }
    }

    private Entrepreneurship mapEntrepreneurshipFromCustomerDatabaseEntity(CustomerDatabaseEntity customerDatabaseEntity) {
        EntrepreneurshipForm entrepreneurshipForm = EntrepreneurshipForm.valueOf(customerDatabaseEntity.getEntrepreneurshipForm());
        String taxPaymentForm = customerDatabaseEntity.getTaxPaymentForm();
        TaxRate taxRate = null;
        for (TaxRate value : TaxRate.values()) {
            if(customerDatabaseEntity.getTaxRate().equals(value.getValue())){
                taxRate = value;
            }
        }

        TaxPaymentForm taxPaymentFormToReturn = null;
        IndustryType industryType = null;
        if ("LumpSumTax".equals(taxPaymentForm)) {
            if ("0.17".equals(taxRate.getValue())) {
                industryType = IndustryType.SOFTWARE_DEVELOPER;
            }
            if ("0.15".equals(taxRate.getValue())) {
                industryType = IndustryType.DOCTOR;
            }
            if ("0.085".equals(taxRate.getValue())) {
                industryType = IndustryType.TENANT;
            }
            if ("0.055".equals(taxRate.getValue())) {
                industryType = IndustryType.FARMER;
            }
            taxPaymentFormToReturn = new LumpSumTax(industryType);
        }else if("FlatTax".equals(taxPaymentForm)){
            taxPaymentFormToReturn = new FlatTax();
        }else {
            taxPaymentFormToReturn = new GeneralTax();

        }

        return new Entrepreneurship(entrepreneurshipForm, taxPaymentFormToReturn);
    }


}

