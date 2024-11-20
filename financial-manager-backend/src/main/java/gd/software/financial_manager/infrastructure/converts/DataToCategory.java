package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.infrastructure.dtos.CategoryData;

public class DataToCategory {

    public static Category convert(CategoryData categoryData) {
        return new Category(categoryData.id(), categoryData.name(), CategoryType.valueOf(categoryData.type()));
    }
}
