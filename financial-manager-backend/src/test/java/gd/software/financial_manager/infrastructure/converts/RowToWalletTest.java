package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;
import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;
import gd.software.financial_manager.infrastructure.persistence.relational.WalletRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RowToWalletTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID BOND_ID = UUID.randomUUID();
    private static final UUID STOCK_ID = UUID.randomUUID();
    private static final UUID REIT_ID = UUID.randomUUID();
    private static final BondRow BOND = BondRow.builder().id(BOND_ID).build();
    private static final StockRow STOCK = StockRow.builder().id(STOCK_ID).build();
    private static final ReitRow REIT = ReitRow.builder().id(REIT_ID).build();
    private static final BigDecimal AMOUNT = new BigDecimal("32.00");

    @Test
    void should_convert() {
        WalletRow row = WalletRow.builder()
                .id(ID)
                .amount(AMOUNT)
                .bonds(List.of(BOND))
                .stocks(List.of(STOCK))
                .reits(List.of(REIT))
                .build();
        Wallet wallet = RowToWallet.convert(row);

        assertThat(wallet).isNotNull();
        assertThat(wallet.id()).isEqualByComparingTo(ID);
        assertThat(wallet.totalAmount()).isEqualTo(AMOUNT);
        assertThat(wallet.bonds()).isNotEmpty();
        assertThat(wallet.bonds().get(0)).isNotNull();
        assertThat(wallet.bonds().get(0).id()).isEqualTo(BOND_ID);
        assertThat(wallet.stocks()).isNotEmpty();
        assertThat(wallet.stocks().get(0)).isNotNull();
        assertThat(wallet.stocks().get(0).id()).isEqualTo(STOCK_ID);
        assertThat(wallet.reits()).isNotEmpty();
        assertThat(wallet.reits().get(0)).isNotNull();
        assertThat(wallet.reits().get(0).id()).isEqualTo(REIT_ID);
    }
}
