package gd.software.financial_manager.domain.usecase.budget;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.domain.usecase.collections.AllBudgets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetUseCasesTest {

    @Mock
    private AllBudgets allBudgets;

    @InjectMocks
    private CreateBudget createBudget;

    @InjectMocks
    private FetchBudgets fetchBudgets;

    @InjectMocks
    private DeleteBudget deleteBudget;

    private static final UUID BUDGET_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void createBudget_should_delegate_to_allBudgets_save() {
        Budget input = new Budget(null, CATEGORY_ID, new BigDecimal("500.00"), 9, 2026);
        Budget saved = new Budget(BUDGET_ID, CATEGORY_ID, new BigDecimal("500.00"), 9, 2026);
        when(allBudgets.save(input)).thenReturn(saved);

        Budget result = createBudget.use(input);

        assertThat(result).isEqualTo(saved);
        verify(allBudgets).save(input);
    }

    @Test
    void fetchBudgets_byUserId_should_return_list() {
        Budget budget = new Budget(BUDGET_ID, CATEGORY_ID, new BigDecimal("500.00"), 9, 2026);
        when(allBudgets.byUserId(USER_ID)).thenReturn(List.of(budget));

        List<Budget> result = fetchBudgets.byUserId(USER_ID);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).categoryId()).isEqualTo(CATEGORY_ID);
        verify(allBudgets).byUserId(USER_ID);
    }

    @Test
    void fetchBudgets_byUserIdAndMonthAndYear_should_return_list() {
        Budget budget = new Budget(BUDGET_ID, CATEGORY_ID, new BigDecimal("500.00"), 9, 2026);
        when(allBudgets.byUserIdAndMonthAndYear(USER_ID, 9, 2026)).thenReturn(List.of(budget));

        List<Budget> result = fetchBudgets.byUserIdAndMonthAndYear(USER_ID, 9, 2026);

        assertThat(result).hasSize(1);
        verify(allBudgets).byUserIdAndMonthAndYear(USER_ID, 9, 2026);
    }

    @Test
    void deleteBudget_should_delegate_to_allBudgets_remove() {
        deleteBudget.use(BUDGET_ID);

        verify(allBudgets).remove(BUDGET_ID);
    }
}
