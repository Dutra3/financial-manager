package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.infrastructure.dtos.BondResponse;
import gd.software.financial_manager.infrastructure.dtos.ReitResponse;
import gd.software.financial_manager.infrastructure.dtos.StockResponse;
import gd.software.financial_manager.infrastructure.dtos.WalletResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class WalletToResponseTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID BOND_ID = UUID.randomUUID();
    private static final UUID STOCK_ID = UUID.randomUUID();
    private static final UUID REIT_ID = UUID.randomUUID();
    private static final BondResponse BOND = new BondResponse();
    private static final StockResponse STOCK = new BondResponse();
    private static final ReitResponse REIT = new BondResponse();

    @Test
    void should_convert() {
        Wallet wallet = new Wallet();
        WalletResponse response = WalletToResponse.convert(wallet);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo();
        assertThat(response.totalAmount()).isEqualTo();
        assertThat(response.bonds()).isNotEmpty();
        assertThat(response.bonds().get(0)).isNotNull();
        assertThat(response.bonds().get(0).id()).isEqualTo();
        assertThat(response.stocks()).isNotEmpty();
        assertThat(response.stocks().get(0)).isNotNull();
        assertThat(response.stocks().get(0).id()).isEqualTo();
        assertThat(response.reits()).isNotEmpty();
        assertThat(response.reits().get(0)).isNotNull();
        assertThat(response.reits().get(0).id()).isEqualTo();
    }
}
