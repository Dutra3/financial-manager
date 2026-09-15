package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.infrastructure.persistence.relational.BudgetRow;

public class RowToBudget {

    public static Budget convert(BudgetRow row) {
        return new Budget(row.getId(), row.getCategory().getId(), row.getAmount(),
                row.getMonth(), row.getYear());
    }
}
