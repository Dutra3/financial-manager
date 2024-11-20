package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;

public class CategoryToRow {

    public static CategoryRow convert(Category category) {
        return CategoryRow.builder()
                .id(category.id())
                .name(category.name())
                .type(category.type().name())
                .build();
    }
}
