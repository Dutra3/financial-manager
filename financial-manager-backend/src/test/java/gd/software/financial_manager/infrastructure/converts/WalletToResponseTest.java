package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.infrastructure.dtos.WalletResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class WalletToResponseTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID BOND_ID = UUID.randomUUID();
    private static final UUID STOCK_ID = UUID.randomUUID();
    private static final UUID REIT_ID = UUID.randomUUID();
    private static final BigDecimal AMOUNT = new BigDecimal("235.00");
    private static final Bond BOND = new Bond(BOND_ID);
    private static final Stock STOCK = new Stock(STOCK_ID);
    private static final Reit REIT = new Reit(REIT_ID);

    @Test
    void should_convert() {
        Wallet wallet = new Wallet(ID, AMOUNT, List.of(BOND), List.of(STOCK), List.of(REIT));
        WalletResponse response = WalletToResponse.convert(wallet);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(ID);
        assertThat(response.totalAmount()).isEqualTo(AMOUNT);
        assertThat(response.bonds()).isNotEmpty();
        assertThat(response.bonds().get(0)).isNotNull();
        assertThat(response.bonds().get(0).id()).isEqualTo(BOND_ID);
        assertThat(response.stocks()).isNotEmpty();
        assertThat(response.stocks().get(0)).isNotNull();
        assertThat(response.stocks().get(0).id()).isEqualTo(STOCK_ID);
        assertThat(response.reits()).isNotEmpty();
        assertThat(response.reits().get(0)).isNotNull();
        assertThat(response.reits().get(0).id()).isEqualTo(REIT_ID);
    }
}
