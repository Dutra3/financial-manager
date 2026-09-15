package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.infrastructure.dtos.BudgetDTO;
import gd.software.financial_manager.infrastructure.persistence.relational.BudgetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryTypeRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BudgetToDTOTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void should_convert_row_to_dto() {
        CategoryRow category = CategoryRow.builder()
                .id(CATEGORY_ID)
                .name("Food")
                .type(CategoryTypeRow.DEBIT)
                .build();
        BudgetRow row = BudgetRow.builder()
                .id(ID)
                .category(category)
                .amount(new BigDecimal("500.00"))
                .month(9)
                .year(2026)
                .build();

        BudgetDTO dto = BudgetToDTO.convert(row);

        assertThat(dto.id()).isEqualTo(ID);
        assertThat(dto.categoryId()).isEqualTo(CATEGORY_ID);
        assertThat(dto.amount()).isEqualByComparingTo("500.00");
        assertThat(dto.month()).isEqualTo(9);
        assertThat(dto.year()).isEqualTo(2026);
        assertThat(dto.categoryName()).isEqualTo("Food");
    }
}
