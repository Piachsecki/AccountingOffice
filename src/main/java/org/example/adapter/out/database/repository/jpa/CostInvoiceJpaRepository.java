package org.example.adapter.out.database.repository.jpa;

import org.example.adapter.out.database.entity.CostInvoiceDatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CostInvoiceJpaRepository extends JpaRepository<CostInvoiceDatabaseEntity, UUID> {
}
