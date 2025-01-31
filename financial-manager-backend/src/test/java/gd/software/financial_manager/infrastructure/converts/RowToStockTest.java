package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
}
