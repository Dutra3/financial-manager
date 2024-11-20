package gd.software.financial_manager.infrastructure.controllers.category;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.usecase.category.CreateCategory;
import gd.software.financial_manager.domain.usecase.category.DeleteCategory;
import gd.software.financial_manager.domain.usecase.category.FetchCategory;
import gd.software.financial_manager.infrastructure.converts.CategoryToData;
import gd.software.financial_manager.infrastructure.converts.DataToCategory;
import gd.software.financial_manager.infrastructure.dtos.CategoryData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(CategoryEndpoints.class);

    @Autowired
    private CreateCategory createCategory;

    @Autowired
    private FetchCategory fetchCategory;

    @Autowired
    private DeleteCategory deleteCategory;

    @PostMapping
    public ResponseEntity<CategoryData> save(@RequestBody CategoryData categoryData) {
        Category category = DataToCategory.convert(categoryData);
        Category savedCategory = createCategory.use(category);

        logger.info("Created category {}.", savedCategory.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryToData.convert(savedCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryData> fetchCategory(@PathVariable UUID id) throws Exception {
        Category category = fetchCategory.by(id);
        logger.info("Get category {}.", category.name());

        return ResponseEntity.status(HttpStatus.OK).body(CategoryToData.convert(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryData>> fetchAllCategories() {
        List<Category> categories = fetchCategory.all();
        logger.info("Get all categories.");

        return ResponseEntity.status(HttpStatus.OK).body(CategoryToData.convert(categories));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable UUID id) {
        deleteCategory.remove(id);
        return ResponseEntity.noContent().build();
    }
}
