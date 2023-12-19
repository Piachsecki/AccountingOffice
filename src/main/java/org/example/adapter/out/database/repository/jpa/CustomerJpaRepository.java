package org.example.adapter.out.database.repository.jpa;

import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository

public interface CustomerJpaRepository extends JpaRepository<CustomerDatabaseEntity, UUID> {
    Optional<CustomerDatabaseEntity> findByCustomerId(UUID customerId);
}
