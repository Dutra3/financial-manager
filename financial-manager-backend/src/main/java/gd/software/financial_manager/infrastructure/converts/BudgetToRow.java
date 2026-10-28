package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.infrastructure.persistence.relational.BudgetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;

public class BudgetToRow {

    public static BudgetRow convert(Budget budget) {
        BudgetRow.BudgetRowBuilder builder = BudgetRow.builder()
                .id(budget.id())
                .category(CategoryRow.builder().id(budget.categoryId()).build())
                .amount(budget.amount())
                .month(budget.month())
                .year(budget.year());

        if (budget.userId() != null) {
            builder.user(UserRow.builder().id(budget.userId()).build());
        }

        return builder.build();
    }
}
