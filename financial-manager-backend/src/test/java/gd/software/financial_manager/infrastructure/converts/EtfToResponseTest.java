package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.infrastructure.dtos.EtfResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

class EtfToResponseTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID ID_TWO = UUID.randomUUID();
    private static final String NAME = "Vanguard S&P 500 ETF";
    private static final String TICKER = "VOO";
    private static final String DESCRIPTION = "ETF tracking the S&P 500 index";
    private static final String TYPE = "Index Fund";
    private static final String INDUSTRY_SEGMENT = "Equities";
    private static final BigDecimal PRICE = new BigDecimal("400.25");

    @Test
    void should_convert() {
        Etf etf = new Etf(ID, NAME, TICKER, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, PRICE);
        EtfResponse etfResponse = EtfToResponse.convert(etf);

        assertThat(etfResponse).isNotNull();
        assertThat(etfResponse.id()).isEqualTo(ID);
        assertThat(etfResponse.name()).isEqualTo(NAME);
        assertThat(etfResponse.ticker()).isEqualTo(TICKER);
        assertThat(etfResponse.description()).isEqualTo(DESCRIPTION);
        assertThat(etfResponse.type()).isEqualTo(TYPE);
        assertThat(etfResponse.industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(etfResponse.price()).isEqualTo(PRICE);
    }

    @Test
    void should_convert_list() {
        Etf etf = new Etf(ID, NAME, TICKER, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, PRICE);
        Etf etfTwo = new Etf(ID_TWO, "ETF 2", "ETF2", "Second ETF", "Type B", "Sector 2", new BigDecimal("200.00"));

        List<EtfResponse> etfResponses = EtfToResponse.convert(List.of(etf, etfTwo));

        assertThat(etfResponses).hasSize(2);
        assertThat(etfResponses.get(0)).isNotNull();
        assertThat(etfResponses.get(0).id()).isEqualTo(ID);
        assertThat(etfResponses.get(0).name()).isEqualTo(NAME);
        assertThat(etfResponses.get(0).ticker()).isEqualTo(TICKER);
        assertThat(etfResponses.get(0).description()).isEqualTo(DESCRIPTION);
        assertThat(etfResponses.get(0).type()).isEqualTo(TYPE);
        assertThat(etfResponses.get(0).industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(etfResponses.get(0).price()).isEqualTo(PRICE);
        assertThat(etfResponses.get(1)).isNotNull();
        assertThat(etfResponses.get(1).id()).isEqualTo(ID_TWO);
        assertThat(etfResponses.get(1).name()).isEqualTo("ETF 2");
        assertThat(etfResponses.get(1).ticker()).isEqualTo("ETF2");
        assertThat(etfResponses.get(1).description()).isEqualTo("Second ETF");
        assertThat(etfResponses.get(1).type()).isEqualTo("Type B");
        assertThat(etfResponses.get(1).industrySegment()).isEqualTo("Sector 2");
        assertThat(etfResponses.get(1).price()).isEqualTo(new BigDecimal("200.00"));
    }
}
