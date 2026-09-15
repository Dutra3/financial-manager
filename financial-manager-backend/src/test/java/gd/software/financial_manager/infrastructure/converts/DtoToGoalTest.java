package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.infrastructure.dtos.GoalDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DtoToGoalTest {

    private static final UUID ID = UUID.randomUUID();

    @Test
    void should_convert_dto_to_goal() {
        GoalDTO dto = new GoalDTO(ID, "Emergency Fund", "Save 6 months",
                LocalDate.of(2026, 12, 31), new BigDecimal("50000.00"), false);

        Goal goal = DtoToGoal.convert(dto);

        assertThat(goal.id()).isEqualTo(ID);
        assertThat(goal.name()).isEqualTo("Emergency Fund");
        assertThat(goal.description()).isEqualTo("Save 6 months");
        assertThat(goal.targetDate()).isEqualTo(LocalDate.of(2026, 12, 31));
        assertThat(goal.targetAmount()).isEqualByComparingTo("50000.00");
        assertThat(goal.isAchieved()).isFalse();
    }

    @Test
    void should_default_isAchieved_to_false_when_null() {
        GoalDTO dto = new GoalDTO(ID, "Vacation", "Trip to Japan",
                LocalDate.of(2027, 6, 1), new BigDecimal("15000.00"), null);

        Goal goal = DtoToGoal.convert(dto);

        assertThat(goal.isAchieved()).isFalse();
    }
}
