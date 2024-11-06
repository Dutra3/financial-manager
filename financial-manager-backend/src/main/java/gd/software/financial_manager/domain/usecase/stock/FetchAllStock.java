package gd.software.financial_manager.domain.usecase.stock;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.usecase.collections.AllStocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FetchAllStock {

    private static final Logger logger = LoggerFactory.getLogger(FetchAllStock.class);

    private final AllStocks allStocks;

    public FetchAllStock(AllStocks allStocks) {
        this.allStocks = allStocks;
    }

    public List<Stock> use() {
        return allStocks.all();
    }
}
