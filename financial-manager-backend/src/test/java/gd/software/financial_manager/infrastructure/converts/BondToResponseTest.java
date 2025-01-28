package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.dtos.BondResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BondToResponseTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID ID_TWO = UUID.randomUUID();
    private static final String NAME = "Name Bond";
    private static final String NAME_TWO = "Name Bond Two";
    private static final String DESCRIPTION = "Description";
    private static final String DESCRIPTION_TWO = "Description Two";
    private static final String TYPE = "Type";
    private static final String TYPE_TWO = "Type Two";
    private static final String INDUSTRY_SEGMENT = "Industry Segment";
    private static final String INDUSTRY_SEGMENT_TWO = "Industry Segment Two";
    private static final BigDecimal PRICE = new BigDecimal("32.00");
    private static final BigDecimal PRICE_TWO = new BigDecimal("54.21");

    @Test
    void should_convert() {
        Bond bond = new Bond(ID, NAME, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, PRICE);
        BondResponse response = BondToResponse.convert(bond);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualByComparingTo(ID);
        assertThat(response.name()).isEqualTo(NAME);
        assertThat(response.description()).isEqualTo(DESCRIPTION);
        assertThat(response.type()).isEqualTo(TYPE);
        assertThat(response.industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(response.price()).isEqualByComparingTo(PRICE);
    }

    @Test
    void should_convert_list() {
        Bond bond = new Bond(ID, NAME, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, PRICE);
        Bond bondTwo = new Bond(ID_TWO, NAME_TWO, DESCRIPTION_TWO, TYPE_TWO, INDUSTRY_SEGMENT_TWO, PRICE_TWO);
        List<BondResponse> responses = BondToResponse.convert(List.of(bond, bondTwo));

        assertThat(responses).isNotEmpty();
        assertThat(responses.get(0)).isNotNull();
        assertThat(responses.get(0).id()).isEqualByComparingTo(ID);
        assertThat(responses.get(0).name()).isEqualTo(NAME);
        assertThat(responses.get(0).description()).isEqualTo(DESCRIPTION);
        assertThat(responses.get(0).type()).isEqualTo(TYPE);
        assertThat(responses.get(0).industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(responses.get(0).price()).isEqualByComparingTo(PRICE);
        assertThat(responses.get(1)).isNotNull();
        assertThat(responses.get(1).id()).isEqualByComparingTo(ID_TWO);
        assertThat(responses.get(1).name()).isEqualTo(NAME_TWO);
        assertThat(responses.get(1).description()).isEqualTo(DESCRIPTION_TWO);
        assertThat(responses.get(1).type()).isEqualTo(TYPE_TWO);
        assertThat(responses.get(1).industrySegment()).isEqualTo(INDUSTRY_SEGMENT_TWO);
        assertThat(responses.get(1).price()).isEqualByComparingTo(PRICE_TWO);
    }
}
