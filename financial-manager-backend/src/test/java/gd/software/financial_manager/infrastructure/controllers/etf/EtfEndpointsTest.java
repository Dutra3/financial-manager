package gd.software.financial_manager.infrastructure.controllers.etf;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.domain.usecase.etf.CreateEtfTransaction;
import gd.software.financial_manager.domain.usecase.etf.FetchEtf;
import gd.software.financial_manager.infrastructure.dtos.EtfTransactionDTO;
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
class EtfEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateEtfTransaction createEtfTransaction;

    @MockBean
    private FetchEtf fetchEtf;

    private static final UUID ETF_ID = UUID.randomUUID();
    private static final UUID TRANSACTION_ID = UUID.randomUUID();

    @Test
    void should_save_etf_transaction_and_return_201() throws Exception {
        EtfTransactionDTO request = new EtfTransactionDTO(null, ETF_ID,
                new BigDecimal("20"), new BigDecimal("80.00"), LocalDate.of(2025, 2, 1));
        Etf etf = new Etf(ETF_ID, "IVVB11", "IVVB11", "S&P 500 ETF", "ETF", "International",
                new BigDecimal("80.00"));
        EtfTransaction saved = new EtfTransaction(TRANSACTION_ID, etf, new BigDecimal("20"),
                new BigDecimal("80.00"), LocalDate.of(2025, 2, 1));
        when(createEtfTransaction.use(any(EtfTransaction.class))).thenReturn(saved);

        mockMvc.perform(post("/etfs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TRANSACTION_ID.toString()));
    }

    @Test
    void should_fetch_etf_by_id_and_return_200() throws Exception {
        Etf etf = new Etf(ETF_ID, "IVVB11", "IVVB11", "S&P 500 ETF", "ETF", "International",
                new BigDecimal("80.00"));
        when(fetchEtf.by(ETF_ID)).thenReturn(etf);

        mockMvc.perform(get("/etfs/{id}", ETF_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticker").value("IVVB11"));
    }

    @Test
    void should_fetch_all_etfs_and_return_200() throws Exception {
        Etf etf = new Etf(ETF_ID, "IVVB11", "IVVB11", "S&P 500 ETF", "ETF", "International",
                new BigDecimal("80.00"));
        when(fetchEtf.all()).thenReturn(List.of(etf));

        mockMvc.perform(get("/etfs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticker").value("IVVB11"));
    }
}
