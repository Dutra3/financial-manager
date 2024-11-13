package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BondRepository extends JpaRepository<BondRow, UUID> {
}
