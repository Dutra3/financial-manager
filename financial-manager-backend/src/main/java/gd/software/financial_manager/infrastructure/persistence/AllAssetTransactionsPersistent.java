package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllAssetTransactions;
import gd.software.financial_manager.infrastructure.converts.AssetTransactionToRow;
import gd.software.financial_manager.infrastructure.converts.RowToAssetTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTransactionRow;
import gd.software.financial_manager.infrastructure.persistence.repository.AssetRepository;
import gd.software.financial_manager.infrastructure.persistence.repository.AssetTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AllAssetTransactionsPersistent implements AllAssetTransactions {

    private static final Logger logger = LoggerFactory.getLogger(AllAssetTransactionsPersistent.class);

    @Autowired
    private AssetTransactionRepository repository;

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public AssetTransaction save(AssetTransaction assetTransaction) {
        AssetRow asset = assetRepository.findById(assetTransaction.asset().id())
                .orElseThrow(() -> new EntityNotFoundException("Asset not found: " + assetTransaction.asset().id()));

        AssetTransactionRow row = AssetTransactionToRow.convert(assetTransaction);
        row.setAsset(asset);

        AssetTransactionRow saved = repository.save(row);
        logger.info("Saved asset transaction {} for user {}.", saved.getId(), assetTransaction.userId());

        return RowToAssetTransaction.convert(saved);
    }

    @Override
    public List<AssetTransaction> allBy(UUID userId) {
        logger.info("Find all Asset Transactions by user id {}.", userId);
        return repository.findByUserId(userId).stream().map(RowToAssetTransaction::convert).toList();
    }
}
