package gd.software.financial_manager.domain.usecase.budget;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.domain.model.BudgetAlert;
import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllBudgets;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetAlertsTest {

    @Mock
    private AllBudgets allBudgets;

    @Mock
    private AllTransactions allTransactions;

    @Mock
    private AllCategories allCategories;

    @InjectMocks
    private BudgetAlerts budgetAlerts;

    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void should_return_OK_when_spending_below_threshold() {
        Budget budget = new Budget(UUID.randomUUID(), CATEGORY_ID, new BigDecimal("1000.00"), 9, 2026);
        when(allBudgets.byUserIdAndMonthAndYear(USER_ID, 9, 2026)).thenReturn(List.of(budget));
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.of(new Category(CATEGORY_ID, "Food", CategoryType.DEBIT)));
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of(
                new Transaction(UUID.randomUUID(), "Lunch", "", new BigDecimal("300.00"),
                        LocalDate.of(2026, 9, 15), new Category(CATEGORY_ID, "Food", CategoryType.DEBIT))
        ));

        List<BudgetAlert> alerts = budgetAlerts.forUserAndMonth(USER_ID, 9, 2026);

        assertThat(alerts).hasSize(1);
        assertThat(alerts.get(0).status()).isEqualTo(BudgetAlert.AlertStatus.OK);
        assertThat(alerts.get(0).percentage()).isEqualByComparingTo("30.00");
    }

    @Test
    void should_return_WARNING_when_spending_above_80_percent() {
        Budget budget = new Budget(UUID.randomUUID(), CATEGORY_ID, new BigDecimal("1000.00"), 9, 2026);
        when(allBudgets.byUserIdAndMonthAndYear(USER_ID, 9, 2026)).thenReturn(List.of(budget));
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.of(new Category(CATEGORY_ID, "Food", CategoryType.DEBIT)));
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of(
                new Transaction(UUID.randomUUID(), "Dinner", "", new BigDecimal("850.00"),
                        LocalDate.of(2026, 9, 20), new Category(CATEGORY_ID, "Food", CategoryType.DEBIT))
        ));

        List<BudgetAlert> alerts = budgetAlerts.forUserAndMonth(USER_ID, 9, 2026);

        assertThat(alerts.get(0).status()).isEqualTo(BudgetAlert.AlertStatus.WARNING);
        assertThat(alerts.get(0).percentage()).isEqualByComparingTo("85.00");
    }

    @Test
    void should_return_EXCEEDED_when_spending_above_budget() {
        Budget budget = new Budget(UUID.randomUUID(), CATEGORY_ID, new BigDecimal("500.00"), 9, 2026);
        when(allBudgets.byUserIdAndMonthAndYear(USER_ID, 9, 2026)).thenReturn(List.of(budget));
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.of(new Category(CATEGORY_ID, "Food", CategoryType.DEBIT)));
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of(
                new Transaction(UUID.randomUUID(), "Expensive dinner", "", new BigDecimal("600.00"),
                        LocalDate.of(2026, 9, 25), new Category(CATEGORY_ID, "Food", CategoryType.DEBIT))
        ));

        List<BudgetAlert> alerts = budgetAlerts.forUserAndMonth(USER_ID, 9, 2026);

        assertThat(alerts.get(0).status()).isEqualTo(BudgetAlert.AlertStatus.EXCEEDED);
        assertThat(alerts.get(0).percentage()).isEqualByComparingTo("120.00");
    }

    @Test
    void should_return_empty_when_no_budgets() {
        when(allBudgets.byUserIdAndMonthAndYear(USER_ID, 9, 2026)).thenReturn(List.of());

        List<BudgetAlert> alerts = budgetAlerts.forUserAndMonth(USER_ID, 9, 2026);

        assertThat(alerts).isEmpty();
    }

    @Test
    void should_not_count_credit_transactions() {
        Budget budget = new Budget(UUID.randomUUID(), CATEGORY_ID, new BigDecimal("1000.00"), 9, 2026);
        when(allBudgets.byUserIdAndMonthAndYear(USER_ID, 9, 2026)).thenReturn(List.of(budget));
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.of(new Category(CATEGORY_ID, "Salary", CategoryType.CREDIT)));
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of(
                new Transaction(UUID.randomUUID(), "Salary", "", new BigDecimal("5000.00"),
                        LocalDate.of(2026, 9, 5), new Category(CATEGORY_ID, "Salary", CategoryType.CREDIT))
        ));

        List<BudgetAlert> alerts = budgetAlerts.forUserAndMonth(USER_ID, 9, 2026);

        assertThat(alerts.get(0).spentAmount()).isEqualByComparingTo("0.00");
        assertThat(alerts.get(0).status()).isEqualTo(BudgetAlert.AlertStatus.OK);
    }

    @Test
    void should_filter_transactions_by_month_and_year() {
        Budget budget = new Budget(UUID.randomUUID(), CATEGORY_ID, new BigDecimal("1000.00"), 9, 2026);
        when(allBudgets.byUserIdAndMonthAndYear(USER_ID, 9, 2026)).thenReturn(List.of(budget));
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.of(new Category(CATEGORY_ID, "Food", CategoryType.DEBIT)));
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of(
                new Transaction(UUID.randomUUID(), "Sep lunch", "", new BigDecimal("200.00"),
                        LocalDate.of(2026, 9, 15), new Category(CATEGORY_ID, "Food", CategoryType.DEBIT)),
                new Transaction(UUID.randomUUID(), "Oct lunch", "", new BigDecimal("800.00"),
                        LocalDate.of(2026, 10, 15), new Category(CATEGORY_ID, "Food", CategoryType.DEBIT))
        ));

        List<BudgetAlert> alerts = budgetAlerts.forUserAndMonth(USER_ID, 9, 2026);

        assertThat(alerts.get(0).spentAmount()).isEqualByComparingTo("200.00");
    }
}
