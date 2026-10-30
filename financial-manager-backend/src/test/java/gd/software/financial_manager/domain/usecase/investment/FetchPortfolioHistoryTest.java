package gd.software.financial_manager.domain.usecase.investment;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.domain.model.AssetType;
import gd.software.financial_manager.domain.usecase.collections.AllAssetTransactions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchPortfolioHistoryTest {

    @Mock
    private AllAssetTransactions allAssetTransactions;

    @InjectMocks
    private FetchPortfolioHistory fetchPortfolioHistory;

    private static final UUID USER_ID = UUID.randomUUID();

    private AssetTransaction tx(AssetTransactionType type, String qty, String price, LocalDate date) {
        Asset asset = new Asset(UUID.randomUUID(), "X", "N", AssetType.STOCK, null, null, null,
                new BigDecimal("10"), null, null, null, null, null, null, null);
        return new AssetTransaction(null, asset, USER_ID, type, new BigDecimal(qty),
                new BigDecimal(price), date);
    }

    @Test
    void should_accumulate_invested_per_month() {
        when(allAssetTransactions.allBy(USER_ID)).thenReturn(List.of(
                tx(AssetTransactionType.SELL, "5", "60.00", LocalDate.of(2025, 3, 5)),
                tx(AssetTransactionType.BUY, "10", "50.00", LocalDate.of(2025, 1, 10)),
                tx(AssetTransactionType.BUY, "5", "20.00", LocalDate.of(2025, 2, 10))));

        var history = fetchPortfolioHistory.byUser(USER_ID);

        assertThat(history.get(YearMonth.of(2025, 1))).isEqualByComparingTo("500.00");
        assertThat(history.get(YearMonth.of(2025, 2))).isEqualByComparingTo("600.00");
        assertThat(history.get(YearMonth.of(2025, 3))).isEqualByComparingTo("300.00");
    }

    @Test
    void should_return_empty_when_no_transactions() {
        when(allAssetTransactions.allBy(USER_ID)).thenReturn(List.of());
        assertThat(fetchPortfolioHistory.byUser(USER_ID)).isEmpty();
    }
}
