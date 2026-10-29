package gd.software.financial_manager.infrastructure.controllers.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.transaction.CreateTransaction;
import gd.software.financial_manager.domain.usecase.transaction.DeleteTransaction;
import gd.software.financial_manager.domain.usecase.transaction.FetchTransaction;
import gd.software.financial_manager.domain.usecase.transaction.UpdateTransaction;
import gd.software.financial_manager.infrastructure.dtos.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
class TransactionEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateTransaction createTransaction;

    @MockBean
    private FetchTransaction fetchTransaction;

    @MockBean
    private DeleteTransaction deleteTransaction;

    @MockBean
    private UpdateTransaction updateTransaction;

    private static final UUID TRANSACTION_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @org.junit.jupiter.api.AfterEach
    void clearSecurityContext() {
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
    }

    private void authenticateAs(UUID userId) {
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        userId.toString(), null, java.util.Collections.emptyList()));
    }

    @Test
    void should_return_403_when_fetching_transactions_of_another_user() throws Exception {
        authenticateAs(USER_ID);

        mockMvc.perform(get("/transactions/{id}", UUID.randomUUID()))
                .andExpect(status().isForbidden());

        verifyNoInteractions(fetchTransaction);
    }

    @Test
    void should_allow_fetch_when_path_user_matches_authenticated_user() throws Exception {
        authenticateAs(USER_ID);
        when(fetchTransaction.all(eq(USER_ID), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/transactions/{id}", USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_400_when_transaction_body_is_invalid() throws Exception {
        TransactionDTO request = new TransactionDTO(null, "", "Missing name and category",
                null, null, null, USER_ID, null);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createTransaction);
    }

    @Test
    void should_create_transaction_and_return_201() throws Exception {
        TransactionDTO request = new TransactionDTO(null, "Salary", "Monthly salary",
                new BigDecimal("5000.00"), LocalDate.of(2025, 1, 5), CATEGORY_ID, USER_ID, null);
        Category category = new Category(CATEGORY_ID, "Income", CategoryType.CREDIT);
        Transaction saved = new Transaction(TRANSACTION_ID, "Salary", "Monthly salary",
                new BigDecimal("5000.00"), LocalDate.of(2025, 1, 5), category);
        when(createTransaction.use(any(Transaction.class))).thenReturn(saved);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Salary"))
                .andExpect(jsonPath("$.amount").value(5000.00));

        verify(createTransaction).use(any(Transaction.class));
    }

    @Test
    void should_fetch_all_transactions_by_user_and_return_200() throws Exception {
        Category category = new Category(CATEGORY_ID, "Food", CategoryType.DEBIT);
        Transaction transaction = new Transaction(TRANSACTION_ID, "Lunch", "Restaurant",
                new BigDecimal("50.00"), LocalDate.of(2025, 1, 10), category);
        when(fetchTransaction.all(eq(USER_ID), any())).thenReturn(new PageImpl<>(List.of(transaction)));

        mockMvc.perform(get("/transactions/{id}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Lunch"))
                .andExpect(jsonPath("$.content[0].category").value("Food"))
                .andExpect(jsonPath("$.content[0].type").value("DEBIT"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void should_delete_transaction_and_return_204() throws Exception {
        doNothing().when(deleteTransaction).remove(TRANSACTION_ID);

        mockMvc.perform(delete("/transactions/{id}", TRANSACTION_ID))
                .andExpect(status().isNoContent());

        verify(deleteTransaction).remove(TRANSACTION_ID);
    }

    @Test
    void should_update_transaction_and_return_200() throws Exception {
        TransactionDTO request = new TransactionDTO(TRANSACTION_ID, "Lunch Updated", "Restaurant updated",
                new BigDecimal("75.00"), LocalDate.of(2025, 1, 11), CATEGORY_ID, USER_ID, null);
        Category category = new Category(CATEGORY_ID, "Food", CategoryType.DEBIT);
        Transaction updated = new Transaction(TRANSACTION_ID, "Lunch Updated", "Restaurant updated",
                new BigDecimal("75.00"), LocalDate.of(2025, 1, 11), category);
        when(updateTransaction.use(any(Transaction.class))).thenReturn(updated);

        mockMvc.perform(put("/transactions/{id}", TRANSACTION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lunch Updated"))
                .andExpect(jsonPath("$.amount").value(75.00));

        verify(updateTransaction).use(any(Transaction.class));
    }

    @Test
    void should_return_404_when_updating_nonexistent_transaction() throws Exception {
        TransactionDTO request = new TransactionDTO(TRANSACTION_ID, "Lunch", "Restaurant",
                new BigDecimal("50.00"), LocalDate.of(2025, 1, 10), CATEGORY_ID, USER_ID, null);
        when(updateTransaction.use(any(Transaction.class)))
                .thenThrow(new jakarta.persistence.EntityNotFoundException("Transaction not found with id " + TRANSACTION_ID));

        mockMvc.perform(put("/transactions/{id}", TRANSACTION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
