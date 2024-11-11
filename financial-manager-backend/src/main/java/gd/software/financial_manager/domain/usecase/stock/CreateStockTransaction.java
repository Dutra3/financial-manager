package gd.software.financial_manager.domain.usecase.stock;

import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllStockTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateStockTransaction {

    private static final Logger logger = LoggerFactory.getLogger(CreateStockTransaction.class);

    private final AllStockTransactions allStockTransactions;

    public CreateStockTransaction(AllStockTransactions allStockTransactions) {
        this.allStockTransactions = allStockTransactions;
    }

    public StockTransaction use(StockTransaction stockTransaction) {
        logger.info("Creating stock transaction for {}.", stockTransaction.stock().name());
        return allStockTransactions.save(stockTransaction);
    }
}
