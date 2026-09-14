package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_find_stock_by_ticker() {
        entityManager.persist(StockRow.builder()
                .name("Petrobras")
                .ticker("PETR4")
                .description("Oil company")
                .type("Stock")
                .industrySegment("Energy")
                .tagAlong(new BigDecimal("0.80"))
                .price(new BigDecimal("35.50"))
                .isBesst(true)
                .isNewMarket(true)
                .build());
        entityManager.flush();
        entityManager.clear();

        Optional<StockRow> found = stockRepository.findByTicker("PETR4");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Petrobras");
        assertThat(found.get().getPrice()).isEqualByComparingTo("35.50");
    }

    @Test
    void should_return_empty_when_ticker_not_found() {
        Optional<StockRow> found = stockRepository.findByTicker("NONEXISTENT");

        assertThat(found).isEmpty();
    }
}
