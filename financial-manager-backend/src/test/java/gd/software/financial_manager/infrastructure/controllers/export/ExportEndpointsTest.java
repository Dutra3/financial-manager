package gd.software.financial_manager.infrastructure.controllers.export;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExportEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AllTransactions allTransactions;

    private static final UUID USER_ID = UUID.randomUUID();

    @Test
    void should_export_transactions_as_csv() throws Exception {
        UUID txId = UUID.randomUUID();
        Category category = new Category(UUID.randomUUID(), "Food", CategoryType.DEBIT);
        Transaction transaction = new Transaction(txId, "Lunch", "Restaurant",
                new BigDecimal("25.50"), LocalDate.of(2026, 9, 15), category);
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of(transaction));

        MvcResult result = mockMvc.perform(get("/export/transactions/{userId}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=transactions.csv"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("ID,Name,Description,Amount,PaymentDate,Category,Type");
        assertThat(content).contains("Lunch");
        assertThat(content).contains("Restaurant");
        assertThat(content).contains("25.50");
        assertThat(content).contains("Food");
        assertThat(content).contains("DEBIT");
    }

    @Test
    void should_escape_csv_values_with_commas() throws Exception {
        Category category = new Category(UUID.randomUUID(), "Food, Drink", CategoryType.DEBIT);
        Transaction transaction = new Transaction(UUID.randomUUID(), "Lunch, dinner",
                "Note with, comma", new BigDecimal("10.00"), LocalDate.of(2026, 9, 15), category);
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of(transaction));

        MvcResult result = mockMvc.perform(get("/export/transactions/{userId}", USER_ID))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("\"Food, Drink\"");
        assertThat(content).contains("\"Lunch, dinner\"");
    }

    @Test
    void should_export_empty_csv_when_no_transactions() throws Exception {
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of());

        MvcResult result = mockMvc.perform(get("/export/transactions/{userId}", USER_ID))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("ID,Name,Description,Amount,PaymentDate,Category,Type");
        assertThat(content.split("\n")).hasSize(1);
    }
}
