package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

class RowToStockTest {

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
    private static final boolean IS_BESST = true;
    private static final boolean IS_NEW_MARKET = true;

    @Test
    void should_convert() {
        StockRow stockRow = StockRow.builder()
                .id(ID)
                .name(NAME)
                .ticker(TICKER)
                .description(DESCRIPTION)
                .type(TYPE)
                .industrySegment(INDUSTRY_SEGMENT)
                .tagAlong(TAG_ALONG)
                .price(PRICE)
                .isBesst(IS_BESST)
                .isNewMarket(IS_NEW_MARKET)
                .build();
        Stock stock = RowToStock.convert(stockRow);

        assertThat(stock).isNotNull();
        assertThat(stock.id()).isEqualTo(ID);
        assertThat(stock.name()).isEqualTo(NAME);
        assertThat(stock.ticker()).isEqualTo(TICKER);
        assertThat(stock.description()).isEqualTo(DESCRIPTION);
        assertThat(stock.type()).isEqualTo(TYPE);
        assertThat(stock.industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(stock.tagAlong()).isEqualTo(TAG_ALONG);
        assertThat(stock.price()).isEqualTo(PRICE);
        assertThat(stock.isBesst()).isEqualTo(IS_BESST);
        assertThat(stock.isNewMarket()).isEqualTo(IS_NEW_MARKET);
    }

    @Test
    void should_convert_with_financial_metrics() {
        StockRow stockRow = StockRow.builder()
                .id(ID)
                .name(NAME)
                .ticker(TICKER)
                .description(DESCRIPTION)
                .type(TYPE)
                .industrySegment(INDUSTRY_SEGMENT)
                .tagAlong(TAG_ALONG)
                .price(PRICE)
                .peRatio(PE_RATIO)
                .dividendYield(DIVIDEND_YIELD)
                .pbRatio(PB_RATIO)
                .lastDividend(LAST_DIVIDEND)
                .isBesst(IS_BESST)
                .isNewMarket(IS_NEW_MARKET)
                .build();
        Stock stock = RowToStock.convert(stockRow);

        assertThat(stock.peRatio()).isEqualTo(PE_RATIO);
        assertThat(stock.dividendYield()).isEqualTo(DIVIDEND_YIELD);
        assertThat(stock.pbRatio()).isEqualTo(PB_RATIO);
        assertThat(stock.lastDividend()).isEqualTo(LAST_DIVIDEND);
    }

    @Test
    void should_convert_list() {
        List<StockRow> rows = List.of(StockRow.builder()
                .id(ID)
                .name(NAME)
                .ticker(TICKER)
                .description(DESCRIPTION)
                .type(TYPE)
                .industrySegment(INDUSTRY_SEGMENT)
                .tagAlong(TAG_ALONG)
                .price(PRICE)
                .isBesst(IS_BESST)
                .isNewMarket(IS_NEW_MARKET)
                .build());
        List<Stock> stocks = RowToStock.convert(rows);

        assertThat(stocks).isNotEmpty();
        assertThat(stocks.get(0)).isNotNull();
        assertThat(stocks.get(0).id()).isEqualTo(ID);
        assertThat(stocks.get(0).name()).isEqualTo(NAME);
        assertThat(stocks.get(0).ticker()).isEqualTo(TICKER);
        assertThat(stocks.get(0).description()).isEqualTo(DESCRIPTION);
        assertThat(stocks.get(0).type()).isEqualTo(TYPE);
        assertThat(stocks.get(0).industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(stocks.get(0).tagAlong()).isEqualTo(TAG_ALONG);
        assertThat(stocks.get(0).price()).isEqualTo(PRICE);
        assertThat(stocks.get(0).isBesst()).isEqualTo(IS_BESST);
        assertThat(stocks.get(0).isNewMarket()).isEqualTo(IS_NEW_MARKET);
    }
}
