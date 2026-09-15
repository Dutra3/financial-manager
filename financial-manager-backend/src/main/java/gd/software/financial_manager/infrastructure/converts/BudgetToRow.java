package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.infrastructure.persistence.relational.BudgetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;

public class BudgetToRow {

    public static BudgetRow convert(Budget budget) {
        return BudgetRow.builder()
                .id(budget.id())
                .category(CategoryRow.builder().id(budget.categoryId()).build())
                .amount(budget.amount())
                .month(budget.month())
                .year(budget.year())
                .build();
    }
}
