package gd.software.financial_manager.domain.usecase.goal;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.domain.usecase.collections.AllGoals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalUseCasesTest {

    @Mock
    private AllGoals allGoals;

    @InjectMocks
    private CreateGoal createGoal;

    @InjectMocks
    private FetchGoals fetchGoals;

    @InjectMocks
    private DeleteGoal deleteGoal;

    private static final UUID GOAL_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();

    @Test
    void createGoal_should_delegate_to_allGoals_save() {
        Goal input = new Goal(null, "Emergency Fund", "Save 6 months", LocalDate.of(2026, 12, 31),
                new BigDecimal("50000.00"), false);
        Goal saved = new Goal(GOAL_ID, "Emergency Fund", "Save 6 months", LocalDate.of(2026, 12, 31),
                new BigDecimal("50000.00"), false);
        when(allGoals.save(input)).thenReturn(saved);

        Goal result = createGoal.use(input);

        assertThat(result).isEqualTo(saved);
        verify(allGoals).save(input);
    }

    @Test
    void fetchGoals_byUserId_should_return_list() {
        Goal goal = new Goal(GOAL_ID, "Emergency Fund", "Save 6 months", LocalDate.of(2026, 12, 31),
                new BigDecimal("50000.00"), false);
        when(allGoals.byUserId(USER_ID)).thenReturn(List.of(goal));

        List<Goal> result = fetchGoals.byUserId(USER_ID);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Emergency Fund");
        verify(allGoals).byUserId(USER_ID);
    }

    @Test
    void deleteGoal_should_delegate_to_allGoals_remove() {
        deleteGoal.use(GOAL_ID);

        verify(allGoals).remove(GOAL_ID);
    }
}
