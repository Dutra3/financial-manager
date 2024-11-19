package gd.software.financial_manager.domain.usecase.category;

import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteCategory {

    private static final Logger logger = LoggerFactory.getLogger(DeleteCategory.class);

    private final AllCategories allCategories;

    public DeleteCategory(AllCategories allCategories) {
        this.allCategories = allCategories;
    }

    public void remove(UUID id) {
        allCategories.remove(id);
    }
}
