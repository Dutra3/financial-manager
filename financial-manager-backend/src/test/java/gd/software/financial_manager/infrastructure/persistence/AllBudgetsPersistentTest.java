package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.infrastructure.persistence.relational.BudgetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryTypeRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllBudgetsPersistent.class)
class AllBudgetsPersistentTest {

    @Autowired
    private AllBudgetsPersistent allBudgets;

    @Autowired
    private TestEntityManager entityManager;

    private UserRow persistUser() {
        return entityManager.persist(UserRow.builder()
                .email("budget@test.com")
                .password("encoded")
                .build());
    }

    private CategoryRow persistCategory() {
        return entityManager.persist(CategoryRow.builder()
                .name("Food")
                .type(CategoryTypeRow.DEBIT)
                .build());
    }

    @Test
    void should_save_budget_and_return_domain() {
        UserRow user = persistUser();
        CategoryRow category = persistCategory();
        entityManager.flush();

        Budget budget = new Budget(null, category.getId(), new BigDecimal("500.00"), 9, 2026);
        Budget saved = allBudgets.save(budget);

        assertThat(saved).isNotNull();
        assertThat(saved.categoryId()).isEqualTo(category.getId());
        assertThat(saved.amount()).isEqualByComparingTo("500.00");
        assertThat(saved.month()).isEqualTo(9);
        assertThat(saved.year()).isEqualTo(2026);
    }

    @Test
    void should_find_budgets_by_user_id() {
        UserRow user = persistUser();
        CategoryRow category = persistCategory();
        entityManager.persist(BudgetRow.builder()
                .category(category)
                .amount(new BigDecimal("500.00"))
                .month(9)
                .year(2026)
                .user(user)
                .build());
        entityManager.persist(BudgetRow.builder()
                .category(category)
                .amount(new BigDecimal("300.00"))
                .month(10)
                .year(2026)
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        List<Budget> budgets = allBudgets.byUserId(user.getId());

        assertThat(budgets).hasSize(2);
    }

    @Test
    void should_find_budgets_by_user_and_month_and_year() {
        UserRow user = persistUser();
        CategoryRow category = persistCategory();
        entityManager.persist(BudgetRow.builder()
                .category(category)
                .amount(new BigDecimal("500.00"))
                .month(9)
                .year(2026)
                .user(user)
                .build());
        entityManager.persist(BudgetRow.builder()
                .category(category)
                .amount(new BigDecimal("300.00"))
                .month(10)
                .year(2026)
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        List<Budget> budgets = allBudgets.byUserIdAndMonthAndYear(user.getId(), 9, 2026);

        assertThat(budgets).hasSize(1);
        assertThat(budgets.get(0).month()).isEqualTo(9);
    }

    @Test
    void should_remove_budget_by_id() {
        UserRow user = persistUser();
        CategoryRow category = persistCategory();
        BudgetRow row = entityManager.persist(BudgetRow.builder()
                .category(category)
                .amount(new BigDecimal("500.00"))
                .month(9)
                .year(2026)
                .user(user)
                .build());
        entityManager.flush();

        allBudgets.remove(row.getId());

        assertThat(entityManager.find(BudgetRow.class, row.getId())).isNull();
    }
}
