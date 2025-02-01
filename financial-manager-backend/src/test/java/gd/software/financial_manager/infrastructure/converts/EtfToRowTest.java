package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class EtfToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Vanguard S&P 500 ETF";
    private static final String TICKER = "VOO";
    private static final String DESCRIPTION = "ETF tracking the S&P 500 index";
    private static final String TYPE = "Index Fund";
    private static final String INDUSTRY_SEGMENT = "Equities";
    private static final BigDecimal PRICE = new BigDecimal("400.25");

    @Test
    void should_convert() {
        Etf etf = new Etf(ID, NAME, TICKER, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, PRICE);
        EtfRow etfRow = EtfToRow.convert(etf);

        assertThat(etfRow).isNotNull();
        assertThat(etfRow.getId()).isEqualTo(ID);
        assertThat(etfRow.getName()).isEqualTo(NAME);
        assertThat(etfRow.getTicker()).isEqualTo(TICKER);
        assertThat(etfRow.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(etfRow.getType()).isEqualTo(TYPE);
        assertThat(etfRow.getIndustrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(etfRow.getPrice()).isEqualTo(PRICE);
    }
}
