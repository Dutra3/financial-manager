package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.usecase.collections.AllStocks;
import gd.software.financial_manager.infrastructure.converts.RowToStock;
import gd.software.financial_manager.infrastructure.persistence.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AllStocksPersistent implements AllStocks {

    private static final Logger logger = LoggerFactory.getLogger(AllStocksPersistent.class);

    @Autowired
    StockRepository repository;

    @Override
    public Optional<Stock> findStockByTicker(String ticker) {
        logger.info("Find Stock with ticker {}.", ticker);
        return repository.findByTicker(ticker).map(RowToStock::convert);
    }

    @Override
    public Optional<Stock> by(UUID id) {
        logger.info("Find Stock with id {}.", id);
        return repository.findById(id).map(RowToStock::convert);
    }

    @Override
    public List<Stock> all() {
        logger.info("Find all Stocks.");
        return repository.findAll().stream().map(RowToStock::convert).toList();
    }
}
