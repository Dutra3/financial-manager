package gd.software.financial_manager.domain.usecase.asset;

import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllAssetTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateAssetTransaction {

    private static final Logger logger = LoggerFactory.getLogger(CreateAssetTransaction.class);

    private final AllAssetTransactions allAssetTransactions;

    public CreateAssetTransaction(AllAssetTransactions allAssetTransactions) {
        this.allAssetTransactions = allAssetTransactions;
    }

    public AssetTransaction use(AssetTransaction assetTransaction) {
        logger.info("Creating asset transaction for user {}.", assetTransaction.userId());
        return allAssetTransactions.save(assetTransaction);
    }
}
