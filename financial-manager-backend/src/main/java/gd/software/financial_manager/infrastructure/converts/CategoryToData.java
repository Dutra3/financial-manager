package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.infrastructure.dtos.CategoryData;

import java.util.List;

public class CategoryToData {

    public static List<CategoryData> convert(List<Category> categories) {
        return categories.stream()
                .map(CategoryToData::convert)
                .toList();
    }

    public static CategoryData convert(Category category) {
        return new CategoryData(category.id(), category.name(), category.type().name());
    }
}
