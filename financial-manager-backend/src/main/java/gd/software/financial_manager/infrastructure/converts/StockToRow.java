package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;

public class StockToRow {

    public static StockRow convert(Stock stock) {
        return StockRow.builder()
                .id(stock.id())
                .name(stock.name())
                .ticker(stock.ticker())
                .description(stock.description())
                .quantity(stock.quantity())
                .price(stock.price())
                .transactionDate(stock.transactionDate())
                .build();
    }
}
