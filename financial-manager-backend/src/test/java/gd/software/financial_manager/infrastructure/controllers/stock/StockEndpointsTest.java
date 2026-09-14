package gd.software.financial_manager.infrastructure.controllers.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.domain.usecase.stock.CreateStockTransaction;
import gd.software.financial_manager.domain.usecase.stock.FetchStock;
import gd.software.financial_manager.infrastructure.dtos.StockTransactionDTO;
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
class StockEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateStockTransaction createStockTransaction;

    @MockBean
    private FetchStock fetchStock;

    private static final UUID STOCK_ID = UUID.randomUUID();
    private static final UUID TRANSACTION_ID = UUID.randomUUID();

    @Test
    void should_save_stock_transaction_and_return_201() throws Exception {
        StockTransactionDTO request = new StockTransactionDTO(null, STOCK_ID,
                new BigDecimal("100"), new BigDecimal("35.50"), LocalDate.of(2025, 1, 20));
        Stock stock = new Stock(STOCK_ID, "Petrobras", "PETR4", "Oil company", "Stock", "Energy",
                new BigDecimal("0.80"), new BigDecimal("35.50"), true, true);
        StockTransaction saved = new StockTransaction(TRANSACTION_ID, stock, new BigDecimal("100"),
                new BigDecimal("35.50"), LocalDate.of(2025, 1, 20));
        when(createStockTransaction.use(any(StockTransaction.class))).thenReturn(saved);

        mockMvc.perform(post("/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TRANSACTION_ID.toString()));
    }

    @Test
    void should_fetch_stock_by_id_and_return_200() throws Exception {
        Stock stock = new Stock(STOCK_ID, "Petrobras", "PETR4", "Oil company", "Stock", "Energy",
                new BigDecimal("0.80"), new BigDecimal("35.50"), true, true);
        when(fetchStock.by(STOCK_ID)).thenReturn(stock);

        mockMvc.perform(get("/stocks/{id}", STOCK_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticker").value("PETR4"))
                .andExpect(jsonPath("$.isBesst").value(true));
    }

    @Test
    void should_fetch_all_stocks_and_return_200() throws Exception {
        Stock stock = new Stock(STOCK_ID, "Petrobras", "PETR4", "Oil company", "Stock", "Energy",
                new BigDecimal("0.80"), new BigDecimal("35.50"), true, true);
        when(fetchStock.all()).thenReturn(List.of(stock));

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticker").value("PETR4"));
    }
}
