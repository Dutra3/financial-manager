package gd.software.financial_manager.domain.usecase.category;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FetchCategory {

    private static final Logger logger = LoggerFactory.getLogger(FetchCategory.class);

    private final AllCategories allCategories;

    public FetchCategory(AllCategories allCategories) {
        this.allCategories = allCategories;
    }

    public Category by(UUID id) throws Exception {
        logger.info("Find category with id {}.", id);
        return allCategories.by(id).orElseThrow(() -> new Exception("cant_find_categories_with_id"));
    }

    public List<Category> all() {
        logger.info("Find all categories");
        return allCategories.all();
    }
}
