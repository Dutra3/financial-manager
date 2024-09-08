package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Stock;

import java.util.Optional;

public interface AllStocks {

    Stock save(Stock stock);

    Optional<Stock> findStockByTicker(String ticker);
}
