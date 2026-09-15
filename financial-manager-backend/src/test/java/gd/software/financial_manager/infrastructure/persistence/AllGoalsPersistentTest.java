package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.infrastructure.persistence.relational.GoalRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllGoalsPersistent.class)
class AllGoalsPersistentTest {

    @Autowired
    private AllGoalsPersistent allGoals;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_save_goal_and_return_domain() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("goal@test.com")
                .password("encoded")
                .build());
        entityManager.flush();

        GoalRow row = GoalRow.builder()
                .name("Emergency Fund")
                .description("Save 6 months")
                .targetDate(LocalDate.of(2026, 12, 31))
                .targetAmount(new BigDecimal("50000.00"))
                .isAchieved(false)
                .user(user)
                .build();
        entityManager.persist(row);
        entityManager.flush();

        Goal goal = new Goal(row.getId(), "Emergency Fund Updated", "Save 6 months",
                LocalDate.of(2026, 12, 31), new BigDecimal("60000.00"), false);

        Goal saved = allGoals.save(goal);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isEqualTo(row.getId());
        assertThat(saved.name()).isEqualTo("Emergency Fund Updated");
        assertThat(saved.targetAmount()).isEqualByComparingTo("60000.00");
    }

    @Test
    void should_find_goals_by_user_id() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("goal2@test.com")
                .password("encoded")
                .build());
        entityManager.persist(GoalRow.builder()
                .name("Goal 1")
                .description("First")
                .targetDate(LocalDate.of(2026, 6, 1))
                .targetAmount(new BigDecimal("10000.00"))
                .isAchieved(false)
                .user(user)
                .build());
        entityManager.persist(GoalRow.builder()
                .name("Goal 2")
                .description("Second")
                .targetDate(LocalDate.of(2027, 1, 1))
                .targetAmount(new BigDecimal("20000.00"))
                .isAchieved(true)
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        List<Goal> goals = allGoals.byUserId(user.getId());

        assertThat(goals).hasSize(2);
        assertThat(goals.get(0).name()).isEqualTo("Goal 1");
        assertThat(goals.get(1).name()).isEqualTo("Goal 2");
    }

    @Test
    void should_find_goal_by_id() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("goal3@test.com")
                .password("encoded")
                .build());
        GoalRow row = entityManager.persist(GoalRow.builder()
                .name("Vacation")
                .description("Trip to Japan")
                .targetDate(LocalDate.of(2027, 6, 1))
                .targetAmount(new BigDecimal("15000.00"))
                .isAchieved(false)
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        Optional<Goal> found = allGoals.byId(row.getId());

        assertThat(found).isPresent();
        assertThat(found.get().name()).isEqualTo("Vacation");
        assertThat(found.get().targetAmount()).isEqualByComparingTo("15000.00");
    }

    @Test
    void should_return_empty_when_goal_not_found() {
        Optional<Goal> found = allGoals.byId(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void should_remove_goal_by_id() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("goal4@test.com")
                .password("encoded")
                .build());
        GoalRow row = entityManager.persist(GoalRow.builder()
                .name("Delete Me")
                .description("Test deletion")
                .targetDate(LocalDate.of(2026, 1, 1))
                .targetAmount(new BigDecimal("5000.00"))
                .isAchieved(false)
                .user(user)
                .build());
        entityManager.flush();

        allGoals.remove(row.getId());

        assertThat(entityManager.find(GoalRow.class, row.getId())).isNull();
    }
}
