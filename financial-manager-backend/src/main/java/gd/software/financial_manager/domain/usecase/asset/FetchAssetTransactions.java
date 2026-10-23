package gd.software.financial_manager.domain.usecase.asset;

import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllAssetTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FetchAssetTransactions {

    private static final Logger logger = LoggerFactory.getLogger(FetchAssetTransactions.class);

    private final AllAssetTransactions allAssetTransactions;

    public FetchAssetTransactions(AllAssetTransactions allAssetTransactions) {
        this.allAssetTransactions = allAssetTransactions;
    }

    public List<AssetTransaction> allBy(UUID userId) {
        logger.info("Fetch all asset transactions for user {}.", userId);
        return allAssetTransactions.allBy(userId);
    }
}
