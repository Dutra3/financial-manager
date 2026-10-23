package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.AssetTransactionRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetTransactionRepository extends JpaRepository<AssetTransactionRow, UUID> {

    List<AssetTransactionRow> findByUserId(UUID userId);
}
