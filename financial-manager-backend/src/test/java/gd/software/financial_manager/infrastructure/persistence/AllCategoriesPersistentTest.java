package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllCategoriesPersistent.class)
class AllCategoriesPersistentTest {

    @Autowired
    private AllCategoriesPersistent allCategories;

    @Test
    void should_save_and_return_category() {
        Category category = new Category(null, "Food", CategoryType.DEBIT);

        Category saved = allCategories.save(category);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.name()).isEqualTo("Food");
        assertThat(saved.type()).isEqualTo(CategoryType.DEBIT);
    }

    @Test
    void should_find_category_by_id() {
        Category saved = allCategories.save(new Category(null, "Transport", CategoryType.CREDIT));

        Optional<Category> found = allCategories.by(saved.id());

        assertThat(found).isPresent();
        assertThat(found.get().name()).isEqualTo("Transport");
        assertThat(found.get().type()).isEqualTo(CategoryType.CREDIT);
    }

    @Test
    void should_return_empty_when_category_not_found() {
        Optional<Category> found = allCategories.by(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void should_find_all_categories() {
        allCategories.save(new Category(null, "Food", CategoryType.DEBIT));
        allCategories.save(new Category(null, "Salary", CategoryType.CREDIT));

        List<Category> all = allCategories.all();

        assertThat(all).hasSize(2);
    }

    @Test
    void should_remove_category_by_id() {
        Category saved = allCategories.save(new Category(null, "Entertainment", CategoryType.DEBIT));
        UUID id = saved.id();

        allCategories.remove(id);

        assertThat(allCategories.by(id)).isEmpty();
    }
}
