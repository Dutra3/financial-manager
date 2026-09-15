package gd.software.financial_manager.infrastructure.controllers.goal;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.domain.usecase.goal.CreateGoal;
import gd.software.financial_manager.domain.usecase.goal.CurrentBalance;
import gd.software.financial_manager.domain.usecase.goal.DeleteGoal;
import gd.software.financial_manager.domain.usecase.goal.FetchGoals;
import gd.software.financial_manager.infrastructure.dtos.GoalDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GoalEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateGoal createGoal;

    @MockBean
    private FetchGoals fetchGoals;

    @MockBean
    private DeleteGoal deleteGoal;

    @MockBean
    private CurrentBalance currentBalance;

    private static final UUID GOAL_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();

    @Test
    void should_create_goal_and_return_201() throws Exception {
        GoalDTO request = new GoalDTO(null, "Emergency Fund", "Save 6 months",
                LocalDate.of(2026, 12, 31), new BigDecimal("50000.00"), false);
        Goal saved = new Goal(GOAL_ID, "Emergency Fund", "Save 6 months",
                LocalDate.of(2026, 12, 31), new BigDecimal("50000.00"), false);
        when(createGoal.use(any(Goal.class))).thenReturn(saved);

        mockMvc.perform(post("/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Emergency Fund"))
                .andExpect(jsonPath("$.targetAmount").value(50000.00));
    }

    @Test
    void should_fetch_goals_by_user_and_return_200() throws Exception {
        Goal goal = new Goal(GOAL_ID, "Emergency Fund", "Save 6 months",
                LocalDate.of(2026, 12, 31), new BigDecimal("50000.00"), false);
        when(fetchGoals.byUserId(USER_ID)).thenReturn(List.of(goal));

        mockMvc.perform(get("/goals/{userId}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Emergency Fund"))
                .andExpect(jsonPath("$[0].targetAmount").value(50000.00));
    }

    @Test
    void should_delete_goal_and_return_204() throws Exception {
        doNothing().when(deleteGoal).use(GOAL_ID);

        mockMvc.perform(delete("/goals/{id}", GOAL_ID))
                .andExpect(status().isNoContent());

        verify(deleteGoal).use(GOAL_ID);
    }

    @Test
    void should_fetch_current_balance_and_return_200() throws Exception {
        when(currentBalance.forUser(USER_ID)).thenReturn(new BigDecimal("3700.00"));

        mockMvc.perform(get("/goals/balance/{userId}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3700.00));
    }

    @Test
    void should_return_404_when_balance_profile_not_found() throws Exception {
        when(currentBalance.forUser(USER_ID)).thenThrow(new EntityNotFoundException("Profile not found"));

        mockMvc.perform(get("/goals/balance/{userId}", USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found"));
    }
}
