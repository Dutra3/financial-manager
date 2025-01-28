package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RowToBondTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Name Bond";
    private static final String DESCRIPTION = "Description";
    private static final String TYPE = "Type";
    private static final String INDUSTRY_SEGMENT = "Industry Segment";
    private static final BigDecimal PRICE = new BigDecimal("32.00");

    @Test
    void should_convert() {
        BondRow row = BondRow.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .type(TYPE)
                .industrySegment(INDUSTRY_SEGMENT)
                .price(PRICE)
                .build();
        Bond bond = RowToBond.convert(row);

        assertThat(bond).isNotNull();
        assertThat(bond.id()).isEqualByComparingTo(ID);
        assertThat(bond.name()).isEqualTo(NAME);
        assertThat(bond.description()).isEqualTo(DESCRIPTION);
        assertThat(bond.type()).isEqualTo(TYPE);
        assertThat(bond.industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(bond.price()).isEqualByComparingTo(PRICE);
    }
}
