package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.infrastructure.dtos.CategoryResponse;

public class CategoryToResponse {

    public static CategoryResponse convert(Category category) {
        return new CategoryResponse(category.id(), category.name(), category.type().name());
    }
}
