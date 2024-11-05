package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.StockTransactionRow;

public class StockTransactionToRow {

    public static StockTransactionRow convert(StockTransaction stockTransaction) {
        return StockTransactionRow.builder()
                .id(stockTransaction.id())
                .stock(StockToRow.convert(stockTransaction.stock()))
                .quantity(stockTransaction.quantity())
                .price(stockTransaction.price())
                .transactionDate(stockTransaction.transactionDate())
                .build();
    }
}
