package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTransactionRow;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTransactionTypeRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;

public class AssetTransactionToRow {

    public static AssetTransactionRow convert(AssetTransaction assetTransaction) {
        return AssetTransactionRow.builder()
                .id(assetTransaction.id())
                .asset(AssetRow.builder().id(assetTransaction.asset().id()).build())
                .user(UserRow.builder().id(assetTransaction.userId()).build())
                .type(AssetTransactionTypeRow.valueOf(assetTransaction.type().name()))
                .quantity(assetTransaction.quantity())
                .price(assetTransaction.price())
                .transactionDate(assetTransaction.transactionDate())
                .build();
    }
}
