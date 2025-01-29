package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Category Name";
    private static final CategoryType TYPE = CategoryType.CREDIT;

    @Test
    void should_convert() {
        Category category = new Category(ID, NAME, TYPE);
        CategoryRow row = CategoryToRow.convert(category);

        assertThat(row).isNotNull();
        assertThat(row.getId()).isEqualTo(ID);
        assertThat(row.getName()).isEqualTo(NAME);
        assertThat(row.getType().name()).isEqualTo(TYPE.name());
    }
}
