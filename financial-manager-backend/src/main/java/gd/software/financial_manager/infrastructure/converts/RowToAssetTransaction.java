package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTransactionRow;

public class RowToAssetTransaction {

    public static AssetTransaction convert(AssetTransactionRow row) {
        return new AssetTransaction(row.getId(), RowToAsset.convert(row.getAsset()),
                row.getUser().getId(), AssetTransactionType.valueOf(row.getType().name()),
                row.getQuantity(), row.getPrice(), row.getTransactionDate());
    }
}
