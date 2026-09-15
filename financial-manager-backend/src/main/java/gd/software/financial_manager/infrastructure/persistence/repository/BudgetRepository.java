package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.BudgetRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<BudgetRow, UUID> {

    List<BudgetRow> findByUserId(UUID userId);

    List<BudgetRow> findByUserIdAndMonthAndYear(UUID userId, Integer month, Integer year);
}
