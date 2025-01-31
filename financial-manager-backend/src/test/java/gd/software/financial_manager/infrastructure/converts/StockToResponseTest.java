package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.dtos.StockResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

class StockToResponseTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID ID_TWO = UUID.randomUUID();
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
        StockResponse response = StockToResponse.convert(stock);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(ID);
        assertThat(response.name()).isEqualTo(NAME);
        assertThat(response.ticker()).isEqualTo(TICKER);
        assertThat(response.description()).isEqualTo(DESCRIPTION);
        assertThat(response.type()).isEqualTo(TYPE);
        assertThat(response.industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(response.tagAlong()).isEqualTo(TAG_ALONG);
        assertThat(response.price()).isEqualTo(PRICE);
        assertThat(response.isBesst()).isEqualTo(true);
        assertThat(response.isNewMarket()).isEqualTo(true);
    }

    @Test
    void should_convert_list() {
        Stock stock = new Stock(ID, NAME, TICKER, DESCRIPTION, TYPE, INDUSTRY_SEGMENT, TAG_ALONG, PRICE, true, true);
        Stock stockTwo = new Stock(ID_TWO, "Stock2", "STK2", "Desc2", "Type2", "Segment2", BigDecimal.ONE, new BigDecimal("15.00"), true, false);

        List<StockResponse> responses = StockToResponse.convert(List.of(stock, stockTwo));

        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).id()).isEqualTo(ID);
        assertThat(responses.get(0).name()).isEqualTo(NAME);
        assertThat(responses.get(0).ticker()).isEqualTo(TICKER);
        assertThat(responses.get(0).description()).isEqualTo(DESCRIPTION);
        assertThat(responses.get(0).type()).isEqualTo(TYPE);
        assertThat(responses.get(0).industrySegment()).isEqualTo(INDUSTRY_SEGMENT);
        assertThat(responses.get(0).tagAlong()).isEqualTo(TAG_ALONG);
        assertThat(responses.get(0).price()).isEqualTo(PRICE);
        assertThat(responses.get(0).isBesst()).isTrue();
        assertThat(responses.get(0).isNewMarket()).isTrue();
        assertThat(responses.get(1).id()).isEqualTo(ID_TWO);
        assertThat(responses.get(1).name()).isEqualTo("Stock2");
        assertThat(responses.get(1).ticker()).isEqualTo("STK2");
        assertThat(responses.get(1).description()).isEqualTo("Desc2");
        assertThat(responses.get(1).type()).isEqualTo("Type2");
        assertThat(responses.get(1).industrySegment()).isEqualTo("Segment2");
        assertThat(responses.get(1).tagAlong()).isEqualTo(BigDecimal.ONE);
        assertThat(responses.get(1).price()).isEqualTo(new BigDecimal("15.00"));
        assertThat(responses.get(1).isBesst()).isTrue();
        assertThat(responses.get(1).isNewMarket()).isFalse();
    }
}
