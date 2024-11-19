package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.infrastructure.dtos.CategoryResponse;

import java.util.List;

public class CategoryToResponse {

    public static List<CategoryResponse> convert(List<Category> categories) {
        return categories.stream()
                .map(CategoryToResponse::convert)
                .toList();
    }

    public static CategoryResponse convert(Category category) {
        return new CategoryResponse(category.id(), category.name(), category.type().name());
    }
}
