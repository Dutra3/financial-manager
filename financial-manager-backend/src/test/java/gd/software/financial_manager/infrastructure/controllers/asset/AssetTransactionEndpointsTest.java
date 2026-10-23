package gd.software.financial_manager.infrastructure.controllers.asset;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.domain.usecase.asset.CreateAssetTransaction;
import gd.software.financial_manager.domain.usecase.asset.FetchAssetTransactions;
import gd.software.financial_manager.infrastructure.dtos.AssetTransactionDTO;
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
class AssetTransactionEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateAssetTransaction createAssetTransaction;

    @MockBean
    private FetchAssetTransactions fetchAssetTransactions;

    private static final UUID ASSET_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();

    @Test
    void should_create_asset_transaction_and_return_201() throws Exception {
        AssetTransactionDTO request = new AssetTransactionDTO(null, ASSET_ID, USER_ID, "BUY",
                new BigDecimal("10"), new BigDecimal("38.00"), LocalDate.of(2026, 10, 20));
        AssetTransaction saved = new AssetTransaction(UUID.randomUUID(), new Asset(ASSET_ID), USER_ID,
                AssetTransactionType.BUY, new BigDecimal("10"), new BigDecimal("38.00"), LocalDate.of(2026, 10, 20));
        when(createAssetTransaction.use(any(AssetTransaction.class))).thenReturn(saved);

        mockMvc.perform(post("/asset-transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assetId").value(ASSET_ID.toString()))
                .andExpect(jsonPath("$.userId").value(USER_ID.toString()))
                .andExpect(jsonPath("$.type").value("BUY"));
    }

    @Test
    void should_fetch_asset_transactions_by_user() throws Exception {
        AssetTransaction transaction = new AssetTransaction(UUID.randomUUID(), new Asset(ASSET_ID), USER_ID,
                AssetTransactionType.BUY, new BigDecimal("10"), new BigDecimal("38.00"), LocalDate.of(2026, 10, 20));
        when(fetchAssetTransactions.allBy(USER_ID)).thenReturn(List.of(transaction));

        mockMvc.perform(get("/asset-transactions/" + USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(USER_ID.toString()))
                .andExpect(jsonPath("$[0].type").value("BUY"));
    }
}
