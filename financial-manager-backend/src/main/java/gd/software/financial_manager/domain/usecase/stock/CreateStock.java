package gd.software.financial_manager.domain.usecase.stock;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.usecase.collections.AllStocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateStock {

    private static final Logger logger = LoggerFactory.getLogger(CreateStock.class);

    private final AllStocks allStocks;

    public CreateStock(AllStocks allStocks) {
        this.allStocks = allStocks;
    }

    public Stock use(Stock stock) {
        logger.info("Creating stock {}", stock.name());
        Optional<Stock> optionalStock = allStocks.findStockByTicker(stock.ticker());
        if (optionalStock.isPresent()) {
            var stockDB = optionalStock.get();
            stockDB.setQuantity(stockDB.quantity().add(stock.quantity()));
            stockDB.setTransactionDate(stock.transactionDate());

            return allStocks.save(stockDB);
        } else {
            return allStocks.save(stock);
        }
    }
}
