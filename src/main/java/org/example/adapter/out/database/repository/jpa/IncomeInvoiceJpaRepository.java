package org.example.adapter.out.database.repository.jpa;

import org.example.adapter.out.database.entity.IncomeInvoiceDatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface IncomeInvoiceJpaRepository extends JpaRepository<IncomeInvoiceDatabaseEntity, UUID> {
}
