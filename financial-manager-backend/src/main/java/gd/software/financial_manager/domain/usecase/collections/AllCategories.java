package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllCategories {

    Optional<Category> by(UUID id);

    List<Category> all();
}
