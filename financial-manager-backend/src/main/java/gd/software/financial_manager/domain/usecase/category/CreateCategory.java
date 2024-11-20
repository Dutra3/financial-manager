package gd.software.financial_manager.domain.usecase.category;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateCategory {

    private static final Logger logger = LoggerFactory.getLogger(CreateCategory.class);

    private final AllCategories allCategories;

    public CreateCategory(AllCategories allCategories) {
        this.allCategories = allCategories;
    }

    public Category use(Category category) {
        logger.info("Creating category {}.", category.name());
        return allCategories.save(category);
    }
}
