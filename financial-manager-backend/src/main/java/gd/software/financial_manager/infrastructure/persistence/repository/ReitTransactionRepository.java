package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.ReitTransactionRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReitTransactionRepository extends JpaRepository<ReitTransactionRow, UUID> {
}
