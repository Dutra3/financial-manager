package gd.software.financial_manager.infrastructure.controllers.reit;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.domain.usecase.reit.CreateReitTransaction;
import gd.software.financial_manager.domain.usecase.reit.FetchReit;
import gd.software.financial_manager.infrastructure.dtos.ReitTransactionDTO;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReitEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateReitTransaction createReitTransaction;

    @MockBean
    private FetchReit fetchReit;

    private static final UUID REIT_ID = UUID.randomUUID();
    private static final UUID TRANSACTION_ID = UUID.randomUUID();

    @Test
    void should_save_reit_transaction_and_return_201() throws Exception {
        ReitTransactionDTO request = new ReitTransactionDTO(null, REIT_ID,
                new BigDecimal("50"), new BigDecimal("120.00"), LocalDate.of(2025, 1, 25));
        Reit reit = new Reit(REIT_ID, "MXRF11", "MXRF11", "Real Estate Fund", "REIT", "Real Estate",
                new BigDecimal("120.00"));
        ReitTransaction saved = new ReitTransaction(TRANSACTION_ID, reit, new BigDecimal("50"),
                new BigDecimal("120.00"), LocalDate.of(2025, 1, 25));
        when(createReitTransaction.use(any(ReitTransaction.class))).thenReturn(saved);

        mockMvc.perform(post("/reits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TRANSACTION_ID.toString()));
    }

    @Test
    void should_fetch_reit_by_id_and_return_200() throws Exception {
        Reit reit = new Reit(REIT_ID, "MXRF11", "MXRF11", "Real Estate Fund", "REIT", "Real Estate",
                new BigDecimal("120.00"));
        when(fetchReit.by(REIT_ID)).thenReturn(reit);

        mockMvc.perform(get("/reits/{id}", REIT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticker").value("MXRF11"));
    }

    @Test
    void should_fetch_all_reits_and_return_200() throws Exception {
        Reit reit = new Reit(REIT_ID, "MXRF11", "MXRF11", "Real Estate Fund", "REIT", "Real Estate",
                new BigDecimal("120.00"));
        when(fetchReit.all()).thenReturn(List.of(reit));

        mockMvc.perform(get("/reits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticker").value("MXRF11"));
    }
}
