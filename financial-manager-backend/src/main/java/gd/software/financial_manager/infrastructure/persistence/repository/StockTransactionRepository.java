package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.StockTransactionRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockTransactionRepository extends JpaRepository<StockTransactionRow, UUID> {
}
