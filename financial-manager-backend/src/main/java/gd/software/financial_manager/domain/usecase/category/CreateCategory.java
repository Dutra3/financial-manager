package gd.software.financial_manager.domain.usecase.category;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;

public class CreateCategory {

    private final AllCategories allCategories;

    public CreateCategory(AllCategories allCategories) {
        this.allCategories = allCategories;
    }

    public Category create(Category category) {
        return null;
    }
}
