package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.dtos.StockDTO;

public class StockToDTO {

    public static StockDTO convert(Stock stock) {
        return new StockDTO(stock.id(), stock.name(), stock.ticker(), stock.description(), stock.quantity(), stock.price(),
                stock.transactionDate());
    }
}
