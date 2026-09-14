package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class StockToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Banco do Brasil";
    private static final String TICKER = "BBSA3";
    private static final String DESCRIPTION = "Description";
    private static final String TYPE = "Banco";
    private static final String INDUSTRY_SEGMENT = "Bancario";
    private static final BigDecimal TAG_ALONG = new BigDecimal("1.00");
    private static final BigDecimal PRICE = new BigDecimal("22.00");
    private static final BigDecimal PE_RATIO = new BigDecimal("15.50");
    private static final BigDecimal DIVIDEND_YIELD = new BigDecimal("4.20");
    private static final BigDecimal PB_RATIO = new BigDecimal("1.80");
    private static final BigDecimal LAST_DIVIDEND = new BigDecimal("0.95");

    @Test
    void should_convert() {
        Stock stock = new Stock(ID, NAME, TICKER, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, TAG_ALONG, PRICE, null, null, null, null, true, true);
        StockRow row = StockToRow.convert(stock);

        assertThat(row).isNotNull();
        assertThat(row.getId()).isEqualTo(ID);
        assertThat(row.getName()).isEqualTo(NAME);
        assertThat(row.getTicker()).isEqualTo(TICKER);
        assertThat(row.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(row.getType()).isEqualTo(TYPE);
        assertThat(row.getIndustrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(row.getTagAlong()).isEqualTo(TAG_ALONG);
        assertThat(row.getPrice()).isEqualTo(PRICE);
        assertThat(row.getIsBesst()).isEqualTo(true);
        assertThat(row.getIsNewMarket()).isEqualTo(true);
    }

    @Test
    void should_convert_with_financial_metrics() {
        Stock stock = new Stock(ID, NAME, TICKER, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, TAG_ALONG, PRICE,
                PE_RATIO, DIVIDEND_YIELD, PB_RATIO, LAST_DIVIDEND, true, true);
        StockRow row = StockToRow.convert(stock);

        assertThat(row.getPeRatio()).isEqualTo(PE_RATIO);
        assertThat(row.getDividendYield()).isEqualTo(DIVIDEND_YIELD);
        assertThat(row.getPbRatio()).isEqualTo(PB_RATIO);
        assertThat(row.getLastDividend()).isEqualTo(LAST_DIVIDEND);
    }
}
