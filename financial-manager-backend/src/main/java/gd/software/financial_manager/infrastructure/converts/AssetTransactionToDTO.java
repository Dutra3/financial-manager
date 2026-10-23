package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.infrastructure.dtos.AssetTransactionDTO;

public class AssetTransactionToDTO {

    public static AssetTransactionDTO convert(AssetTransaction assetTransaction) {
        return new AssetTransactionDTO(assetTransaction.id(), assetTransaction.asset().id(),
                assetTransaction.userId(), assetTransaction.type().name(), assetTransaction.quantity(),
                assetTransaction.price(), assetTransaction.transactionDate());
    }
}
