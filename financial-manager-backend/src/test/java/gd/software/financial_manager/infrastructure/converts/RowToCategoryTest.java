package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryTypeRow;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RowToCategoryTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Category Name";
    private static final CategoryTypeRow TYPE = CategoryTypeRow.CREDIT;

    @Test
    void should_convert() {
        CategoryRow row = CategoryRow.builder()
                .id(ID)
                .name(NAME)
                .type(TYPE)
                .build();
        Category category = RowToCategory.convert(row);

        assertThat(category).isNotNull();
        assertThat(category.id()).isEqualTo(ID);
        assertThat(category.name()).isEqualTo(NAME);
        assertThat(category.type().name()).isEqualTo(TYPE.name());
    }
}
