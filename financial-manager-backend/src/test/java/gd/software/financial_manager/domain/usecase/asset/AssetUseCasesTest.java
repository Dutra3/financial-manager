package gd.software.financial_manager.domain.usecase.asset;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.domain.model.AssetType;
import gd.software.financial_manager.domain.usecase.collections.AllAssetTransactions;
import gd.software.financial_manager.domain.usecase.collections.AllAssets;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AssetUseCasesTest {

    private static final UUID ASSET_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();

    private final AllAssets allAssets = mock(AllAssets.class);
    private final AllAssetTransactions allAssetTransactions = mock(AllAssetTransactions.class);

    private final FetchAssets fetchAssets = new FetchAssets(allAssets);
    private final CreateAssetTransaction createAssetTransaction = new CreateAssetTransaction(allAssetTransactions);
    private final FetchAssetTransactions fetchAssetTransactions = new FetchAssetTransactions(allAssetTransactions);

    private Asset sampleAsset() {
        return new Asset(ASSET_ID, "PETR4", "Petrobras", AssetType.STOCK, null, null, null,
                new BigDecimal("38.00"), null, null, null, null, null, null, null);
    }

    @Test
    void should_fetch_asset_by_id() throws Exception {
        when(allAssets.by(ASSET_ID)).thenReturn(Optional.of(sampleAsset()));

        Asset asset = fetchAssets.by(ASSET_ID);

        assertThat(asset.ticker()).isEqualTo("PETR4");
        verify(allAssets).by(ASSET_ID);
    }

    @Test
    void should_throw_when_asset_not_found() {
        when(allAssets.by(ASSET_ID)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> fetchAssets.by(ASSET_ID));
    }

    @Test
    void should_fetch_assets_by_type() {
        when(allAssets.allByType(AssetType.STOCK)).thenReturn(List.of(sampleAsset()));

        List<Asset> assets = fetchAssets.allByType(AssetType.STOCK);

        assertThat(assets).hasSize(1);
        verify(allAssets).allByType(AssetType.STOCK);
    }

    @Test
    void should_create_asset_transaction() {
        AssetTransaction transaction = new AssetTransaction(null, sampleAsset(), USER_ID,
                AssetTransactionType.BUY, new BigDecimal("10"), new BigDecimal("38.00"), LocalDate.now());
        when(allAssetTransactions.save(transaction)).thenReturn(transaction);

        AssetTransaction saved = createAssetTransaction.use(transaction);

        assertThat(saved.type()).isEqualTo(AssetTransactionType.BUY);
        verify(allAssetTransactions).save(transaction);
    }

    @Test
    void should_fetch_asset_transactions_by_user() {
        AssetTransaction transaction = new AssetTransaction(UUID.randomUUID(), sampleAsset(), USER_ID,
                AssetTransactionType.BUY, new BigDecimal("10"), new BigDecimal("38.00"), LocalDate.now());
        when(allAssetTransactions.allBy(USER_ID)).thenReturn(List.of(transaction));

        List<AssetTransaction> transactions = fetchAssetTransactions.allBy(USER_ID);

        assertThat(transactions).hasSize(1);
        verify(allAssetTransactions).allBy(USER_ID);
    }
}
