package gd.software.financial_manager.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<WalletRow, UUID> {
}
