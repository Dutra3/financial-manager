package gd.software.financial_manager.infrastructure.controllers.budget;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.domain.model.BudgetAlert;
import gd.software.financial_manager.domain.usecase.budget.BudgetAlerts;
import gd.software.financial_manager.domain.usecase.budget.CreateBudget;
import gd.software.financial_manager.domain.usecase.budget.DeleteBudget;
import gd.software.financial_manager.domain.usecase.budget.FetchBudgets;
import gd.software.financial_manager.infrastructure.dtos.BudgetDTO;
import gd.software.financial_manager.infrastructure.persistence.relational.BudgetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryTypeRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import gd.software.financial_manager.infrastructure.persistence.repository.BudgetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BudgetEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateBudget createBudget;

    @MockBean
    private FetchBudgets fetchBudgets;

    @MockBean
    private DeleteBudget deleteBudget;

    @MockBean
    private BudgetAlerts budgetAlerts;

    @MockBean
    private BudgetRepository budgetRepository;

    private static final UUID BUDGET_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void should_create_budget_and_return_201() throws Exception {
        BudgetDTO request = new BudgetDTO(null, CATEGORY_ID, new BigDecimal("500.00"), 9, 2026, null);
        Budget saved = new Budget(BUDGET_ID, CATEGORY_ID, new BigDecimal("500.00"), 9, 2026);
        when(createBudget.use(any(Budget.class))).thenReturn(saved);

        CategoryRow category = CategoryRow.builder().id(CATEGORY_ID).name("Food").type(CategoryTypeRow.DEBIT).build();
        BudgetRow row = BudgetRow.builder()
                .id(BUDGET_ID)
                .category(category)
                .amount(new BigDecimal("500.00"))
                .month(9)
                .year(2026)
                .build();
        when(budgetRepository.findById(BUDGET_ID)).thenReturn(java.util.Optional.of(row));

        mockMvc.perform(post("/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryName").value("Food"))
                .andExpect(jsonPath("$.amount").value(500.00));
    }

    @Test
    void should_fetch_budgets_by_user_and_return_200() throws Exception {
        CategoryRow category = CategoryRow.builder().id(CATEGORY_ID).name("Food").type(CategoryTypeRow.DEBIT).build();
        BudgetRow row = BudgetRow.builder()
                .id(BUDGET_ID)
                .category(category)
                .amount(new BigDecimal("500.00"))
                .month(9)
                .year(2026)
                .build();
        when(budgetRepository.findByUserId(USER_ID)).thenReturn(List.of(row));

        mockMvc.perform(get("/budgets/{userId}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryName").value("Food"))
                .andExpect(jsonPath("$[0].amount").value(500.00));
    }

    @Test
    void should_fetch_budgets_by_month_and_year() throws Exception {
        CategoryRow category = CategoryRow.builder().id(CATEGORY_ID).name("Food").type(CategoryTypeRow.DEBIT).build();
        BudgetRow row = BudgetRow.builder()
                .id(BUDGET_ID)
                .category(category)
                .amount(new BigDecimal("500.00"))
                .month(9)
                .year(2026)
                .build();
        when(budgetRepository.findByUserIdAndMonthAndYear(USER_ID, 9, 2026)).thenReturn(List.of(row));

        mockMvc.perform(get("/budgets/{userId}/{month}/{year}", USER_ID, 9, 2026))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].month").value(9))
                .andExpect(jsonPath("$[0].year").value(2026));
    }

    @Test
    void should_delete_budget_and_return_204() throws Exception {
        doNothing().when(deleteBudget).use(BUDGET_ID);

        mockMvc.perform(delete("/budgets/{id}", BUDGET_ID))
                .andExpect(status().isNoContent());

        verify(deleteBudget).use(BUDGET_ID);
    }

    @Test
    void should_fetch_alerts_and_return_200() throws Exception {
        BudgetAlert alert = new BudgetAlert(CATEGORY_ID, "Food",
                new BigDecimal("1000.00"), new BigDecimal("850.00"),
                new BigDecimal("85.00"), BudgetAlert.AlertStatus.WARNING);
        when(budgetAlerts.forUserAndMonth(USER_ID, 9, 2026)).thenReturn(List.of(alert));

        mockMvc.perform(get("/budgets/alerts/{userId}/{month}/{year}", USER_ID, 9, 2026))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryName").value("Food"))
                .andExpect(jsonPath("$[0].status").value("WARNING"))
                .andExpect(jsonPath("$[0].percentage").value(85.00));
    }
}
