package gd.software.financial_manager.infrastructure.controllers.dashboard;

import gd.software.financial_manager.domain.model.Installment;
import gd.software.financial_manager.domain.usecase.credit.FetchCredit;
import gd.software.financial_manager.domain.usecase.debit.FetchDebit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DashboardEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FetchDebit fetchDebit;

    @MockBean
    private FetchCredit fetchCredit;

    private static final UUID USER_ID = UUID.randomUUID();

    @Test
    void should_fetch_all_debits_and_return_200() throws Exception {
        when(fetchDebit.all(USER_ID)).thenReturn(List.of(
                new Installment(new BigDecimal("100.00")),
                new Installment(new BigDecimal("50.00"))
        ));

        mockMvc.perform(get("/dashboards/debits/{id}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(100.00))
                .andExpect(jsonPath("$[1].amount").value(50.00));
    }

    @Test
    void should_fetch_all_credits_and_return_200() throws Exception {
        when(fetchCredit.all(USER_ID)).thenReturn(List.of(
                new Installment(new BigDecimal("5000.00"))
        ));

        mockMvc.perform(get("/dashboards/credits/{id}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(5000.00));
    }

    @Test
    void should_return_empty_list_when_no_debits() throws Exception {
        when(fetchDebit.all(USER_ID)).thenReturn(List.of());

        mockMvc.perform(get("/dashboards/debits/{id}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
