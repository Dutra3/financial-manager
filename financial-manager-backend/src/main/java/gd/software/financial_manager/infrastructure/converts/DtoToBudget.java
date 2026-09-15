package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.infrastructure.dtos.BudgetDTO;

public class DtoToBudget {

    public static Budget convert(BudgetDTO dto) {
        return new Budget(dto.id(), dto.categoryId(), dto.amount(), dto.month(), dto.year());
    }
}
