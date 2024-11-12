package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.EtfRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EtfRepository extends JpaRepository<EtfRow, UUID> {
}
