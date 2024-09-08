package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.collections.AllStocks;
import gd.software.financial_manager.domain.usecase.stock.CreateStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class StockConfig {

    @Bean
    @Autowired
    public CreateStock createStock(AllStocks allStocks) {
        return new CreateStock(allStocks);
    }
}
