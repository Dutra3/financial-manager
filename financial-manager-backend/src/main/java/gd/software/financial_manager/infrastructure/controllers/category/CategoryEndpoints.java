package gd.software.financial_manager.infrastructure.controllers.category;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.usecase.category.FetchCategory;
import gd.software.financial_manager.infrastructure.converts.CategoryToResponse;
import gd.software.financial_manager.infrastructure.dtos.CategoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(CategoryEndpoints.class);

    @Autowired
    private FetchCategory fetchCategory;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> fetchCategory(@PathVariable UUID id) throws Exception {
        Category category = fetchCategory.by(id);
        logger.info("Get category {}.", category.name());

        return ResponseEntity.status(HttpStatus.OK).body(CategoryToResponse.convert(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> fetchAllCategories() {
        List<Category> categories = fetchCategory.all();
        logger.info("Get all categories.");

        return ResponseEntity.status(HttpStatus.OK).body(CategoryToResponse.convert(categories));
    }
}
