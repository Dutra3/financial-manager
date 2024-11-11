package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.BondTransactionRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BondTransactionRepository extends JpaRepository<BondTransactionRow, UUID> {
}
