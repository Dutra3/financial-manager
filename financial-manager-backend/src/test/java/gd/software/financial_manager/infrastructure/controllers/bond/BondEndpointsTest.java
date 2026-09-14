package gd.software.financial_manager.infrastructure.controllers.bond;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.domain.usecase.bond.CreateBondTransaction;
import gd.software.financial_manager.domain.usecase.bond.FetchBond;
import gd.software.financial_manager.infrastructure.dtos.BondTransactionDTO;
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
class BondEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateBondTransaction createBondTransaction;

    @MockBean
    private FetchBond fetchBond;

    private static final UUID BOND_ID = UUID.randomUUID();
    private static final UUID TRANSACTION_ID = UUID.randomUUID();

    @Test
    void should_save_bond_transaction_and_return_200() throws Exception {
        BondTransactionDTO request = new BondTransactionDTO(null, BOND_ID,
                new BigDecimal("10"), new BigDecimal("100.50"), LocalDate.of(2025, 1, 15));
        Bond bond = new Bond(BOND_ID, "Tesouro Selic", "Government Bond", "Public", "Treasury", new BigDecimal("100.50"));
        BondTransaction saved = new BondTransaction(TRANSACTION_ID, bond, new BigDecimal("10"),
                new BigDecimal("100.50"), LocalDate.of(2025, 1, 15));
        when(createBondTransaction.use(any(BondTransaction.class))).thenReturn(saved);

        mockMvc.perform(post("/bonds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TRANSACTION_ID.toString()))
                .andExpect(jsonPath("$.bondId").value(BOND_ID.toString()));
    }

    @Test
    void should_fetch_bond_by_id_and_return_200() throws Exception {
        Bond bond = new Bond(BOND_ID, "Tesouro Selic", "Government Bond", "Public", "Treasury", new BigDecimal("100.50"));
        when(fetchBond.by(BOND_ID)).thenReturn(bond);

        mockMvc.perform(get("/bonds/{id}", BOND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(BOND_ID.toString()))
                .andExpect(jsonPath("$.name").value("Tesouro Selic"));
    }

    @Test
    void should_fetch_all_bonds_and_return_200() throws Exception {
        Bond bond = new Bond(BOND_ID, "Tesouro Selic", "Government Bond", "Public", "Treasury", new BigDecimal("100.50"));
        when(fetchBond.all()).thenReturn(List.of(bond));

        mockMvc.perform(get("/bonds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tesouro Selic"));
    }
}
