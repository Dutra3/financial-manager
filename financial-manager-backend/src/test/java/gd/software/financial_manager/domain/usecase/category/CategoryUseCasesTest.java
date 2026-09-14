package gd.software.financial_manager.domain.usecase.category;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCasesTest {

    @Mock
    private AllCategories allCategories;

    @InjectMocks
    private CreateCategory createCategory;

    @InjectMocks
    private FetchCategory fetchCategory;

    @InjectMocks
    private DeleteCategory deleteCategory;

    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void createCategory_should_delegate_to_allCategories_save() {
        Category input = new Category(null, "Food", CategoryType.DEBIT);
        Category saved = new Category(CATEGORY_ID, "Food", CategoryType.DEBIT);
        when(allCategories.save(input)).thenReturn(saved);

        Category result = createCategory.use(input);

        assertThat(result).isEqualTo(saved);
        verify(allCategories).save(input);
    }

    @Test
    void fetchCategory_by_should_return_category_when_found() throws Exception {
        Category category = new Category(CATEGORY_ID, "Food", CategoryType.DEBIT);
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.of(category));

        Category result = fetchCategory.by(CATEGORY_ID);

        assertThat(result).isEqualTo(category);
        verify(allCategories).by(CATEGORY_ID);
    }

    @Test
    void fetchCategory_by_should_throw_when_not_found() {
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fetchCategory.by(CATEGORY_ID))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("cant_find_categories_with_id");
    }

    @Test
    void fetchCategory_all_should_delegate_to_allCategories_all() {
        List<Category> categories = List.of(
                new Category(UUID.randomUUID(), "Food", CategoryType.DEBIT),
                new Category(UUID.randomUUID(), "Salary", CategoryType.CREDIT));
        when(allCategories.all()).thenReturn(categories);

        List<Category> result = fetchCategory.all();

        assertThat(result).isEqualTo(categories);
        verify(allCategories).all();
    }

    @Test
    void deleteCategory_should_delegate_to_allCategories_remove() {
        deleteCategory.remove(CATEGORY_ID);

        verify(allCategories).remove(CATEGORY_ID);
    }
}
