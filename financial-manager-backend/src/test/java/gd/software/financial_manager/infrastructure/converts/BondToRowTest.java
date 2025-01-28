package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BondToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Name Bond";
    private static final String DESCRIPTION = "Description";
    private static final String TYPE = "Type";
    private static final String INDUSTRY_SEGMENT = "Industry Segment";
    private static final BigDecimal PRICE = new BigDecimal("32.00");

    @Test
    void should_convert() {
        Bond bond = new Bond(ID, NAME, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, PRICE);
        BondRow response = BondToRow.convert(bond);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualByComparingTo(ID);
        assertThat(response.getName()).isEqualTo(NAME);
        assertThat(response.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(response.getType()).isEqualTo(TYPE);
        assertThat(response.getIndustrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(response.getPrice()).isEqualByComparingTo(PRICE);
    }
}
