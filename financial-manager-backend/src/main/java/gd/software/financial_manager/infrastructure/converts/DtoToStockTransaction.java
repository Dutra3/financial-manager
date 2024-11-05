package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.infrastructure.dtos.StockTransactionDTO;

import java.util.UUID;

public class DtoToStockTransaction {

    public static StockTransaction convert(StockTransactionDTO stockTransactionDTO) {
        return new StockTransaction(stockTransactionDTO.id(), getStock(stockTransactionDTO.stockId()), stockTransactionDTO.quantity(),
                stockTransactionDTO.price(), stockTransactionDTO.transactionDate());
    }

    private static Stock getStock(UUID id) {
        return new Stock(id);
    }
}
