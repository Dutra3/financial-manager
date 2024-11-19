package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import gd.software.financial_manager.infrastructure.converts.RowToCategory;
import gd.software.financial_manager.infrastructure.persistence.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AllCategoriesPersistent implements AllCategories {

    private static final Logger logger = LoggerFactory.getLogger(AllCategoriesPersistent.class);

    @Autowired
    private CategoryRepository repository;

    @Override
    public Optional<Category> by(UUID id) {
        logger.info("Find Category with id {}.", id);
        return repository.findById(id).map(RowToCategory::convert);
    }

    @Override
    public List<Category> all() {
        logger.info("Find all Categories.");
        return repository.findAll().stream().map(RowToCategory::convert).toList();
    }
}
