package gd.software.financial_manager.infrastructure.controllers.wallet;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.domain.usecase.wallet.FetchWallet;
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
class WalletEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FetchWallet fetchWallet;

    private static final UUID WALLET_ID = UUID.randomUUID();

    @Test
    void should_fetch_wallet_by_id_and_return_200() throws Exception {
        Wallet wallet = new Wallet(WALLET_ID, new BigDecimal("15000.00"), List.of(), List.of(), List.of());
        when(fetchWallet.use(WALLET_ID)).thenReturn(wallet);

        mockMvc.perform(get("/wallets/{id}", WALLET_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(WALLET_ID.toString()))
                .andExpect(jsonPath("$.totalAmount").value(15000.00));
    }
}
