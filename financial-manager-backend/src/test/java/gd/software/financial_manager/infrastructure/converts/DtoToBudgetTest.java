package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.infrastructure.dtos.BudgetDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DtoToBudgetTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void should_convert_dto_to_budget() {
        BudgetDTO dto = new BudgetDTO(ID, CATEGORY_ID, new BigDecimal("500.00"), 9, 2026, "Food");

        Budget budget = DtoToBudget.convert(dto);

        assertThat(budget.id()).isEqualTo(ID);
        assertThat(budget.categoryId()).isEqualTo(CATEGORY_ID);
        assertThat(budget.amount()).isEqualByComparingTo("500.00");
        assertThat(budget.month()).isEqualTo(9);
        assertThat(budget.year()).isEqualTo(2026);
    }
}
