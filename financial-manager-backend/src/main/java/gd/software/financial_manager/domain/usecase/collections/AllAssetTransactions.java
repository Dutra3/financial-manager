package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.AssetTransaction;

import java.util.List;
import java.util.UUID;

public interface AllAssetTransactions {

    AssetTransaction save(AssetTransaction assetTransaction);

    List<AssetTransaction> allBy(UUID userId);
}
