package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetType;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTypeRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllAssetsPersistent.class)
class AllAssetsPersistentTest {

    @Autowired
    private AllAssetsPersistent allAssets;

    @Autowired
    private TestEntityManager entityManager;

    private AssetRow persistAsset(String ticker, AssetTypeRow type) {
        return entityManager.persist(AssetRow.builder()
                .ticker(ticker)
                .name(ticker + " Inc")
                .assetType(type)
                .price(new BigDecimal("100.00"))
                .build());
    }

    @Test
    void should_fetch_asset_by_id() {
        AssetRow row = persistAsset("PETR4", AssetTypeRow.STOCK);
        entityManager.flush();

        Optional<Asset> asset = allAssets.by(row.getId());

        assertThat(asset).isPresent();
        assertThat(asset.get().ticker()).isEqualTo("PETR4");
        assertThat(asset.get().assetType()).isEqualTo(AssetType.STOCK);
    }

    @Test
    void should_fetch_all_assets() {
        persistAsset("PETR4", AssetTypeRow.STOCK);
        persistAsset("HGLG11", AssetTypeRow.REIT);
        entityManager.flush();

        List<Asset> assets = allAssets.all();

        assertThat(assets).hasSize(2);
    }

    @Test
    void should_fetch_assets_by_type() {
        persistAsset("PETR4", AssetTypeRow.STOCK);
        persistAsset("VALE3", AssetTypeRow.STOCK);
        persistAsset("HGLG11", AssetTypeRow.REIT);
        entityManager.flush();

        List<Asset> stocks = allAssets.allByType(AssetType.STOCK);
        List<Asset> reits = allAssets.allByType(AssetType.REIT);

        assertThat(stocks).hasSize(2);
        assertThat(reits).hasSize(1);
        assertThat(reits.get(0).ticker()).isEqualTo("HGLG11");
    }
}
