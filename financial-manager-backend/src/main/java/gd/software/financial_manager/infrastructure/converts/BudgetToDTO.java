package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.infrastructure.dtos.BudgetDTO;
import gd.software.financial_manager.infrastructure.persistence.relational.BudgetRow;

public class BudgetToDTO {

    public static BudgetDTO convert(BudgetRow row) {
        return new BudgetDTO(row.getId(), row.getCategory().getId(), row.getAmount(),
                row.getMonth(), row.getYear(), row.getCategory().getName());
    }
}
