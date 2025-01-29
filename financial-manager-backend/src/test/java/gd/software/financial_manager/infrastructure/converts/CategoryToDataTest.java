package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.infrastructure.dtos.CategoryData;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryToDataTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID ID_TWO = UUID.randomUUID();
    private static final String NAME = "Category Name";
    private static final String NAME_TWO = "Category Name Two";
    private static final CategoryType TYPE = CategoryType.DEBIT;

    @Test
    void should_convert() {
        Category category = new Category(ID, NAME, TYPE);
        CategoryData data = CategoryToData.convert(category);

        assertThat(data).isNotNull();
        assertThat(data.id()).isEqualTo(ID);
        assertThat(data.name()).isEqualTo(NAME);
        assertThat(data.type()).isEqualTo(TYPE.name());
    }

    @Test
    void should_convert_list() {
        Category category = new Category(ID, NAME, TYPE);
        Category categoryTwo = new Category(ID_TWO, NAME_TWO, TYPE);
        List<CategoryData> data = CategoryToData.convert(List.of(category, categoryTwo));

        assertThat(data).isNotEmpty();
        assertThat(data.get(0)).isNotNull();
        assertThat(data.get(0).id()).isEqualTo(ID);
        assertThat(data.get(0).name()).isEqualTo(NAME);
        assertThat(data.get(0).type()).isEqualTo(TYPE.name());
        assertThat(data.get(1)).isNotNull();
        assertThat(data.get(1).id()).isEqualTo(ID_TWO);
        assertThat(data.get(1).name()).isEqualTo(NAME_TWO);
        assertThat(data.get(1).type()).isEqualTo(TYPE.name());
    }
}
