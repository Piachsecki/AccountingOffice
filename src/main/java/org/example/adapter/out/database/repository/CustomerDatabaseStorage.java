package org.example.adapter.out.database.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.CustomerInvoiceMapper;
import org.example.domain.NIP;
import org.example.domain.customer.Customer;
import org.example.port.out.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@Repository
public class CustomerDatabaseStorage implements CustomerRepository {
    private CustomerInvoiceMapper customerInvoiceMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer addCustomer(Customer customer) {

//            Address address = customer.getAddress();
//            AddressDatabaseEntity addressDatabaseEntity = AddressDatabaseEntity
//                    .builder()
//                    .city(address.getCity())
//                    .country(address.getCountry())
//                    .address(address.getAddress())
//                    .postalCode(address.getPostalCode())
//                    .build();

        CustomerDatabaseEntity customerDatabaseEntity = customerInvoiceMapper.customerDomainToCustomerDatabaseEntityMapper(customer);
//
//            CustomerDatabaseEntity customerDatabaseEntity = CustomerDatabaseEntity
//                    .builder()
//                    .name(customer.getName())
//                    .surname(customer.getSurname())
//                    .joinDate(customer.getJoinDate())
//                    .entrepreneurshipForm(customer.getEntrepreneurshipForm().entrepreneurshipForm().toString())
//                    .nip(customer.getNip().value())
//                    .taxPaymentForm(customer.getEntrepreneurshipForm().taxPaymentForm().toString())
//                    .taxRate(customer.getEntrepreneurshipForm().taxPaymentForm().getTaxRate().getValue())
//                    .address(addressDatabaseEntity)
//                    .build();
        entityManager.persist(customerDatabaseEntity);
        entityManager.getTransaction().commit();
        customer.setCustomerId(customerDatabaseEntity.getCustomerId());
//            System.out.println(customer);
        return customer;

    }

    @Override
    public void deleteCustomer(UUID customerId) {

        entityManager.remove(entityManager.find(CustomerDatabaseEntity.class, customerId));
        entityManager.getTransaction().commit();
    }



    @Override
    public void deleteAllCustomers() {
        List<CustomerDatabaseEntity> allCustomers = entityManager.createQuery("SELECT cust FROM CustomerDatabaseEntity cust", CustomerDatabaseEntity.class).getResultList();
        allCustomers.forEach(entityManager::remove);
        entityManager.getTransaction().commit();

    }

    @Override
    public Optional<Customer> findCustomerByNIP(NIP nip) {
        String query = "SELECT cust FROM CustomerDatabaseEntity cust WHERE cust.nip = :nip";
        CustomerDatabaseEntity customerDatabaseEntity = entityManager.createQuery(query, CustomerDatabaseEntity.class)
                .setParameter("nip", nip.toString())
                .getSingleResult();
        entityManager.getTransaction().commit();
        Customer customer = customerInvoiceMapper.customerEntityToCustomerDomainMapper(customerDatabaseEntity);
        return Optional.of(customer);
    }
}




