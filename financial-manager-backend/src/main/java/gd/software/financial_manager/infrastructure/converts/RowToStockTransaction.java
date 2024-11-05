package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.StockTransactionRow;

public class RowToStockTransaction {

    public static StockTransaction convert(StockTransactionRow stockTransactionRow) {
        return new StockTransaction(stockTransactionRow.getId(), RowToStock.convert(stockTransactionRow.getStock()),
                stockTransactionRow.getQuantity(), stockTransactionRow.getPrice(), stockTransactionRow.getTransactionDate());
    }
}
