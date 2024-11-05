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
                .type(stock.type())
                .industrySegment(stock.industrySegment())
                .tagAlong(stock.tagAlong())
                .price(stock.price())
                .isBesst(stock.isBesst())
                .isNewMarket(stock.isNewMarket())
                .build();
    }
}
