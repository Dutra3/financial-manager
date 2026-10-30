package gd.software.financial_manager.domain.usecase.investment;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.domain.model.AssetType;
import gd.software.financial_manager.domain.model.PortfolioPosition;
import gd.software.financial_manager.domain.usecase.collections.AllAssetTransactions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchPortfolioTest {

    @Mock
    private AllAssetTransactions allAssetTransactions;

    @InjectMocks
    private FetchPortfolio fetchPortfolio;

    private static final UUID USER_ID = UUID.randomUUID();

    private Asset asset(String ticker, String price) {
        return new Asset(UUID.randomUUID(), ticker, "Name " + ticker, AssetType.STOCK, null, null,
                null, new BigDecimal(price), null, null, null, null, null, null, null);
    }

    private AssetTransaction tx(Asset asset, AssetTransactionType type, String qty, String price) {
        return new AssetTransaction(null, asset, USER_ID, type, new BigDecimal(qty), new BigDecimal(price),
                LocalDate.of(2025, 1, 1));
    }

    @Test
    void should_compute_position_with_average_price_and_pnl() {
        Asset petr = asset("PETR4", "40.00");
        when(allAssetTransactions.allBy(USER_ID)).thenReturn(List.of(
                tx(petr, AssetTransactionType.BUY, "10", "30.00"),
                tx(petr, AssetTransactionType.BUY, "10", "50.00"),
                tx(petr, AssetTransactionType.DIVIDEND, "1", "20.00")));

        List<PortfolioPosition> positions = fetchPortfolio.byUser(USER_ID);

        assertThat(positions).hasSize(1);
        PortfolioPosition p = positions.get(0);
        assertThat(p.quantity()).isEqualByComparingTo("20");
        assertThat(p.averagePrice()).isEqualByComparingTo("40.00");
        assertThat(p.currentValue()).isEqualByComparingTo("800.00");
        // P&L = 800 - (20*40) + 20 dividend = 20
        assertThat(p.profitLoss()).isEqualByComparingTo("20.00");
    }

    @Test
    void should_exclude_fully_sold_positions() {
        Asset vale = asset("VALE3", "60.00");
        when(allAssetTransactions.allBy(USER_ID)).thenReturn(List.of(
                tx(vale, AssetTransactionType.BUY, "10", "50.00"),
                tx(vale, AssetTransactionType.SELL, "10", "60.00")));

        assertThat(fetchPortfolio.byUser(USER_ID)).isEmpty();
    }

    @Test
    void should_keep_partially_sold_position() {
        Asset vale = asset("VALE3", "60.00");
        when(allAssetTransactions.allBy(USER_ID)).thenReturn(List.of(
                tx(vale, AssetTransactionType.BUY, "10", "50.00"),
                tx(vale, AssetTransactionType.SELL, "4", "60.00")));

        List<PortfolioPosition> positions = fetchPortfolio.byUser(USER_ID);
        assertThat(positions).hasSize(1);
        assertThat(positions.get(0).quantity()).isEqualByComparingTo("6");
    }

    @Test
    void should_return_empty_when_no_transactions() {
        when(allAssetTransactions.allBy(USER_ID)).thenReturn(List.of());
        assertThat(fetchPortfolio.byUser(USER_ID)).isEmpty();
    }

    @Test
    void should_compute_allocation_by_asset_type() {
        Asset petr = asset("PETR4", "40.00");
        Asset mxrf = new Asset(UUID.randomUUID(), "MXRF11", "Maxi", AssetType.REIT, null, null,
                null, new BigDecimal("10.00"), null, null, null, null, null, null, null);
        when(allAssetTransactions.allBy(USER_ID)).thenReturn(List.of(
                tx(petr, AssetTransactionType.BUY, "10", "40.00"),
                tx(mxrf, AssetTransactionType.BUY, "40", "10.00")));

        var allocation = fetchPortfolio.allocationByType(USER_ID);

        assertThat(allocation.get("STOCK")).isEqualByComparingTo("400.00");
        assertThat(allocation.get("REIT")).isEqualByComparingTo("400.00");
    }
}
