package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.dtos.StockResponse;

public class StockToResponse {

    public static StockResponse convert(Stock stock) {
        return new StockResponse(stock.id(), stock.name(), stock.ticker(), stock.description(), stock.type(), stock.industrySegment(),
                stock.tagAlong(), stock.price(), stock.isBesst(), stock.isNewMarket());
    }
}
