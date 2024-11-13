package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.collections.AllStockTransactions;
import gd.software.financial_manager.domain.usecase.collections.AllStocks;
import gd.software.financial_manager.domain.usecase.stock.CreateStockTransaction;
import gd.software.financial_manager.domain.usecase.stock.FetchStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockConfig {

    @Bean
    @Autowired
    public CreateStockTransaction createStockTransaction(AllStockTransactions allStockTransactions) {
        return new CreateStockTransaction(allStockTransactions);
    }

    @Bean
    @Autowired
    public FetchStock fetchStock(AllStocks allStocks) {
        return new FetchStock(allStocks);
    }
}
