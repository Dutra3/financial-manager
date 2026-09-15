package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.infrastructure.dtos.GoalDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GoalToDTOTest {

    private static final UUID ID = UUID.randomUUID();

    @Test
    void should_convert_goal_to_dto() {
        Goal goal = new Goal(ID, "Emergency Fund", "Save 6 months",
                LocalDate.of(2026, 12, 31), new BigDecimal("50000.00"), false);

        GoalDTO dto = GoalToDTO.convert(goal);

        assertThat(dto.id()).isEqualTo(ID);
        assertThat(dto.name()).isEqualTo("Emergency Fund");
        assertThat(dto.description()).isEqualTo("Save 6 months");
        assertThat(dto.targetDate()).isEqualTo(LocalDate.of(2026, 12, 31));
        assertThat(dto.targetAmount()).isEqualByComparingTo("50000.00");
        assertThat(dto.isAchieved()).isFalse();
    }
}
