package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Budget;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllBudgets {

    Budget save(Budget budget);

    List<Budget> byUserId(UUID userId);

    List<Budget> byUserIdAndMonthAndYear(UUID userId, Integer month, Integer year);
    Optional<Budget> byId(UUID id);

    void remove(UUID id);
}
