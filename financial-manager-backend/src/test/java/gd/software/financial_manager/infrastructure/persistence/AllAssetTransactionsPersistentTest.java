package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTypeRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllAssetTransactionsPersistent.class)
class AllAssetTransactionsPersistentTest {

    @Autowired
    private AllAssetTransactionsPersistent allAssetTransactions;

    @Autowired
    private TestEntityManager entityManager;

    private UserRow persistUser() {
        return entityManager.persist(UserRow.builder()
                .email("asset-tx@test.com")
                .password("encoded")
                .build());
    }

    private AssetRow persistAsset() {
        return entityManager.persist(AssetRow.builder()
                .ticker("PETR4")
                .name("Petrobras")
                .assetType(AssetTypeRow.STOCK)
                .price(new BigDecimal("38.00"))
                .build());
    }

    @Test
    void should_save_asset_transaction_with_user() {
        UserRow user = persistUser();
        AssetRow asset = persistAsset();
        entityManager.flush();

        AssetTransaction transaction = new AssetTransaction(null, new Asset(asset.getId()), user.getId(),
                AssetTransactionType.BUY, new BigDecimal("10"), new BigDecimal("38.00"), LocalDate.of(2026, 10, 20));

        AssetTransaction saved = allAssetTransactions.save(transaction);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.userId()).isEqualTo(user.getId());
        assertThat(saved.type()).isEqualTo(AssetTransactionType.BUY);
    }

    @Test
    void should_fetch_asset_transactions_by_user() {
        UserRow user = persistUser();
        AssetRow asset = persistAsset();
        entityManager.flush();

        allAssetTransactions.save(new AssetTransaction(null, new Asset(asset.getId()), user.getId(),
                AssetTransactionType.BUY, new BigDecimal("10"), new BigDecimal("38.00"), LocalDate.of(2026, 10, 20)));
        allAssetTransactions.save(new AssetTransaction(null, new Asset(asset.getId()), user.getId(),
                AssetTransactionType.SELL, new BigDecimal("3"), new BigDecimal("40.00"), LocalDate.of(2026, 10, 21)));

        List<AssetTransaction> transactions = allAssetTransactions.allBy(user.getId());

        assertThat(transactions).hasSize(2);
        assertThat(transactions).allMatch(t -> t.userId().equals(user.getId()));
    }
}
