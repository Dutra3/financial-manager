package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Stock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllStocks {

    Optional<Stock> findStockByTicker(String ticker);

    Optional<Stock> by(UUID id);

    List<Stock> all();
}
