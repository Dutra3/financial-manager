package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.WalletRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<WalletRow, UUID> {
}
