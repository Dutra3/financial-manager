package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.StockTransaction;

public interface AllStockTransactions {

    StockTransaction save(StockTransaction stockTransaction);
}
