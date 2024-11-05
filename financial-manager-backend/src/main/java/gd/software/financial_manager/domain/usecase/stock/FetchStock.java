package gd.software.financial_manager.domain.usecase.stock;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.usecase.collections.AllStocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FetchStock {

    private static final Logger logger = LoggerFactory.getLogger(CreateStockTransaction.class);

    private final AllStocks allStocks;

    public FetchStock(AllStocks allStocks) {
        this.allStocks = allStocks;
    }

    public Stock use(UUID id) throws Exception {
        logger.info("Find stock with id {}",id);
        return allStocks.by(id).orElseThrow(() -> new Exception("cant_find_stock_with_id"));
    }
}
