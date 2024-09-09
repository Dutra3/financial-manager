package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReitRepository extends JpaRepository<ReitRow, UUID> {

    Optional<ReitRow> findByTicker(String ticker);
}
