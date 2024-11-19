package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;

public class RowToCategory {

    public static Category convert(CategoryRow categoryRow) {
        return new Category(categoryRow.getId(), categoryRow.getName(), CategoryType.valueOf(categoryRow.getType()));
    }
}
