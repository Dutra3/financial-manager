package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.usecase.collections.AllStocks;
import gd.software.financial_manager.infrastructure.converts.RowToStock;
import gd.software.financial_manager.infrastructure.converts.StockToRow;
import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;
import gd.software.financial_manager.infrastructure.persistence.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AllStocksPersistent implements AllStocks {

    private static final Logger logger = LoggerFactory.getLogger(AllStocksPersistent.class);

    @Autowired
    StockRepository repository;

    @Override
    public Stock save(Stock stock) {
        StockRow stockRow = StockToRow.convert(stock);

        StockRow savedStockRow = repository.save(stockRow);
        logger.info("Saved stock {}", savedStockRow);

        return RowToStock.convert(savedStockRow);
    }

    @Override
    public Optional<Stock> findStockByTicker(String ticker) {
        logger.info("Find Stock with ticker {}", ticker);

        return repository.findByTicker(ticker).map(RowToStock::convert);
    }
}
