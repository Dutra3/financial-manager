package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.domain.model.AssetType;
import gd.software.financial_manager.infrastructure.dtos.AssetTransactionDTO;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTransactionRow;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTypeRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AssetConvertersTest {

    private static final UUID ASSET_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();

    @Test
    void should_convert_row_to_asset() {
        AssetRow row = AssetRow.builder()
                .id(ASSET_ID)
                .ticker("PETR4")
                .name("Petrobras")
                .assetType(AssetTypeRow.STOCK)
                .price(new BigDecimal("38.00"))
                .peRatio(new BigDecimal("5.5"))
                .build();

        Asset asset = RowToAsset.convert(row);

        assertThat(asset.id()).isEqualTo(ASSET_ID);
        assertThat(asset.ticker()).isEqualTo("PETR4");
        assertThat(asset.assetType()).isEqualTo(AssetType.STOCK);
        assertThat(asset.peRatio()).isEqualByComparingTo("5.5");
    }

    @Test
    void should_convert_dto_to_asset_transaction() {
        AssetTransactionDTO dto = new AssetTransactionDTO(null, ASSET_ID, USER_ID, "SELL",
                new BigDecimal("5"), new BigDecimal("40.00"), LocalDate.of(2026, 10, 22));

        AssetTransaction transaction = DtoToAssetTransaction.convert(dto);

        assertThat(transaction.asset().id()).isEqualTo(ASSET_ID);
        assertThat(transaction.userId()).isEqualTo(USER_ID);
        assertThat(transaction.type()).isEqualTo(AssetTransactionType.SELL);
    }

    @Test
    void should_convert_asset_transaction_to_row_with_user() {
        AssetTransaction transaction = new AssetTransaction(null, new Asset(ASSET_ID), USER_ID,
                AssetTransactionType.BUY, new BigDecimal("10"), new BigDecimal("38.00"), LocalDate.now());

        AssetTransactionRow row = AssetTransactionToRow.convert(transaction);

        assertThat(row.getAsset().getId()).isEqualTo(ASSET_ID);
        assertThat(row.getUser().getId()).isEqualTo(USER_ID);
        assertThat(row.getType().name()).isEqualTo("BUY");
    }
}
