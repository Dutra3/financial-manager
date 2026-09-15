package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Budget;

import java.util.List;
import java.util.UUID;

public interface AllBudgets {

    Budget save(Budget budget);

    List<Budget> byUserId(UUID userId);

    List<Budget> byUserIdAndMonthAndYear(UUID userId, Integer month, Integer year);

    void remove(UUID id);
}
