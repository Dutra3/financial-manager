package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class ReitToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Real Estate Investment Trust";
    private static final String TICKER = "REIT";
    private static final String DESCRIPTION = "ETF that invests in real estate properties";
    private static final String TYPE = "Real Estate Fund";
    private static final String INDUSTRY_SEGMENT = "Real Estate";
    private static final BigDecimal PRICE = new BigDecimal("150.75");

    @Test
    void should_convert() {
        Reit reit = new Reit(ID, NAME, TICKER, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, PRICE);
        ReitRow reitRow = ReitToRow.convert(reit);

        assertThat(reitRow).isNotNull();
        assertThat(reitRow.getId()).isEqualTo(ID);
        assertThat(reitRow.getName()).isEqualTo(NAME);
        assertThat(reitRow.getTicker()).isEqualTo(TICKER);
        assertThat(reitRow.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(reitRow.getType()).isEqualTo(TYPE);
        assertThat(reitRow.getIndustrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(reitRow.getPrice()).isEqualTo(PRICE);
    }
}
