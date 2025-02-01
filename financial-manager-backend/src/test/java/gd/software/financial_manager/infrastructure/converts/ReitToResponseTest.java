package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.infrastructure.dtos.ReitResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

class ReitToResponseTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID ID_TWO = UUID.randomUUID();
    private static final String NAME = "Real Estate Investment Trust";
    private static final String TICKER = "REIT";
    private static final String DESCRIPTION = "ETF that invests in real estate properties";
    private static final String TYPE = "Real Estate Fund";
    private static final String INDUSTRY_SEGMENT = "Real Estate";
    private static final BigDecimal PRICE = new BigDecimal("150.75");

    @Test
    void should_convert() {
        Reit reit = new Reit(ID, NAME, TICKER, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, PRICE);
        ReitResponse reitResponse = ReitToResponse.convert(reit);

        assertThat(reitResponse).isNotNull();
        assertThat(reitResponse.id()).isEqualTo(ID);
        assertThat(reitResponse.name()).isEqualTo(NAME);
        assertThat(reitResponse.ticker()).isEqualTo(TICKER);
        assertThat(reitResponse.description()).isEqualTo(DESCRIPTION);
        assertThat(reitResponse.type()).isEqualTo(TYPE);
        assertThat(reitResponse.industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(reitResponse.price()).isEqualTo(PRICE);
    }

    @Test
    void should_convert_list() {
        Reit reit = new Reit(ID, NAME, TICKER, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, PRICE);
        Reit reitTwo = new Reit(ID_TWO, "Reit 2", "REIT2", "Second REIT", "Fund Type B", "Real Estate", new BigDecimal("200.00"));

        List<ReitResponse> reitResponses = ReitToResponse.convert(List.of(reit, reitTwo));

        assertThat(reitResponses).hasSize(2);
        assertThat(reitResponses.get(0).id()).isEqualTo(ID);
        assertThat(reitResponses.get(0).name()).isEqualTo(NAME);
        assertThat(reitResponses.get(0).ticker()).isEqualTo(TICKER);
        assertThat(reitResponses.get(0).description()).isEqualTo(DESCRIPTION);
        assertThat(reitResponses.get(0).type()).isEqualTo(TYPE);
        assertThat(reitResponses.get(0).industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(reitResponses.get(0).price()).isEqualTo(PRICE);
        assertThat(reitResponses.get(1).id()).isEqualTo(ID_TWO);
        assertThat(reitResponses.get(1).name()).isEqualTo("Reit 2");
        assertThat(reitResponses.get(1).ticker()).isEqualTo("REIT2");
        assertThat(reitResponses.get(1).description()).isEqualTo("Second REIT");
        assertThat(reitResponses.get(1).type()).isEqualTo("Fund Type B");
        assertThat(reitResponses.get(1).industrySegment()).isEqualTo("Real Estate");
        assertThat(reitResponses.get(1).price()).isEqualTo(new BigDecimal("200.00"));
    }
}
