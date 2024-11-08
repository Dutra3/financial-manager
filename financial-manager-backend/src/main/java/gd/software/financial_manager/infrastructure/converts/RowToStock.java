package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;

public class RowToStock {

    public static Stock convert(StockRow stockRow) {
        return new Stock(stockRow.getId(), stockRow.getName(), stockRow.getTicker(), stockRow.getDescription(),
                stockRow.getType(), stockRow.getIndustrySegment(), stockRow.getTagAlong(), stockRow.getPrice(),
                stockRow.getIsBesst(), stockRow.getIsNewMarket());
    }
}
