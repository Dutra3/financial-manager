package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.infrastructure.dtos.StockTransactionDTO;

public class StockTransactionToDTO {

    public static StockTransactionDTO convert(StockTransaction stockTransaction) {
        return new StockTransactionDTO(stockTransaction.id(), stockTransaction.stock().id(), stockTransaction.quantity(),
                stockTransaction.price(), stockTransaction.transactionDate());
    }
}
