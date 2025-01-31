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

    @Test
    void should_convert() {
        Stock stock = new Stock(ID, NAME, TICKER, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, TAG_ALONG, PRICE, true, true);
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
}
