package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.dtos.StockResponse;

import java.util.List;
import java.util.stream.Collectors;

public class StockToResponse {

    public static List<StockResponse> convert(List<Stock> stocks) {
        return stocks.stream()
                .map(StockToResponse::convert)
                .collect(Collectors.toList());
    }

    public static StockResponse convert(Stock stock) {
        return new StockResponse(stock.id(), stock.name(), stock.ticker(), stock.description(), stock.type(), stock.industrySegment(),
                stock.tagAlong(), stock.price(), stock.isBesst(), stock.isNewMarket());
    }
}
