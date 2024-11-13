package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.EtfTransactionRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EtfTransactionRepository extends JpaRepository<EtfTransactionRow, UUID> {
}
