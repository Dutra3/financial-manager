package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

class RowToReitTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Real Estate Investment Trust";
    private static final String TICKER = "REIT";
    private static final String DESCRIPTION = "ETF that invests in real estate properties";
    private static final String TYPE = "Real Estate Fund";
    private static final String INDUSTRY_SEGMENT = "Real Estate";
    private static final BigDecimal PRICE = new BigDecimal("150.75");

    @Test
    void should_convert() {
        ReitRow reitRow = ReitRow.builder()
                .id(ID)
                .name(NAME)
                .ticker(TICKER)
                .description(DESCRIPTION)
                .type(TYPE)
                .industrySegment(INDUSTRY_SEGMENT)
                .price(PRICE)
                .build();
        Reit reit = RowToReit.convert(reitRow);

        assertThat(reit).isNotNull();
        assertThat(reit.id()).isEqualTo(ID);
        assertThat(reit.name()).isEqualTo(NAME);
        assertThat(reit.ticker()).isEqualTo(TICKER);
        assertThat(reit.description()).isEqualTo(DESCRIPTION);
        assertThat(reit.type()).isEqualTo(TYPE);
        assertThat(reit.industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(reit.price()).isEqualTo(PRICE);
    }

    @Test
    void should_convert_list() {
        List<ReitRow> reitRows = List.of(ReitRow.builder()
                .id(ID)
                .name(NAME)
                .ticker(TICKER)
                .description(DESCRIPTION)
                .type(TYPE)
                .industrySegment(INDUSTRY_SEGMENT)
                .price(PRICE)
                .build());
        List<Reit> reits = RowToReit.convert(reitRows);

        assertThat(reits).isNotEmpty();
        assertThat(reits.get(0)).isNotNull();
        assertThat(reits.get(0).id()).isEqualTo(ID);
        assertThat(reits.get(0).name()).isEqualTo(NAME);
        assertThat(reits.get(0).ticker()).isEqualTo(TICKER);
        assertThat(reits.get(0).description()).isEqualTo(DESCRIPTION);
        assertThat(reits.get(0).type()).isEqualTo(TYPE);
        assertThat(reits.get(0).industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(reits.get(0).price()).isEqualTo(PRICE);
    }
}
