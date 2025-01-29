package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.infrastructure.dtos.CategoryData;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DataToCategoryTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Category Name";
    private static final CategoryType TYPE = CategoryType.DEBIT;

    @Test
    void should_convert() {
        CategoryData data = new CategoryData(ID, NAME, TYPE.name());
        Category category = DataToCategory.convert(data);

        assertThat(category).isNotNull();
        assertThat(category.id()).isEqualTo(ID);
        assertThat(category.name()).isEqualTo(NAME);
        assertThat(category.type().name()).isEqualTo(TYPE.name());
    }
}
