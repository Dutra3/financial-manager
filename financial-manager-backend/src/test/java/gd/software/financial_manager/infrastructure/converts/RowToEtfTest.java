package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class RowToEtfTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Vanguard S&P 500 ETF";
    private static final String TICKER = "VOO";
    private static final String DESCRIPTION = "ETF tracking the S&P 500 index";
    private static final String TYPE = "Index Fund";
    private static final String INDUSTRY_SEGMENT = "Equities";
    private static final BigDecimal PRICE = new BigDecimal("400.25");

    @Test
    void should_convert() {
        EtfRow etfRow = EtfRow.builder()
                .id(ID)
                .name(NAME)
                .ticker(TICKER)
                .description(DESCRIPTION)
                .type(TYPE)
                .industrySegment(INDUSTRY_SEGMENT)
                .price(PRICE)
                .build();
        Etf etf = RowToEtf.convert(etfRow);

        assertThat(etf).isNotNull();
        assertThat(etf.id()).isEqualTo(ID);
        assertThat(etf.name()).isEqualTo(NAME);
        assertThat(etf.ticker()).isEqualTo(TICKER);
        assertThat(etf.description()).isEqualTo(DESCRIPTION);
        assertThat(etf.type()).isEqualTo(TYPE);
        assertThat(etf.industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(etf.price()).isEqualTo(PRICE);
    }
}
