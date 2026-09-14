package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllStocksPersistent.class)
class AllStocksPersistentTest {

    @Autowired
    private AllStocksPersistent allStocks;

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

        Optional<Stock> found = allStocks.findStockByTicker("PETR4");

        assertThat(found).isPresent();
        assertThat(found.get().name()).isEqualTo("Petrobras");
        assertThat(found.get().price()).isEqualByComparingTo("35.50");
    }

    @Test
    void should_return_empty_when_ticker_not_found() {
        Optional<Stock> found = allStocks.findStockByTicker("NONEXISTENT");

        assertThat(found).isEmpty();
    }

    @Test
    void should_find_stock_by_id() {
        StockRow row = entityManager.persist(StockRow.builder()
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

        Optional<Stock> found = allStocks.by(row.getId());

        assertThat(found).isPresent();
        assertThat(found.get().ticker()).isEqualTo("PETR4");
    }

    @Test
    void should_return_empty_when_stock_id_not_found() {
        Optional<Stock> found = allStocks.by(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void should_find_all_stocks() {
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
        entityManager.persist(StockRow.builder()
                .name("Vale")
                .ticker("VALE3")
                .description("Mining company")
                .type("Stock")
                .industrySegment("Mining")
                .tagAlong(new BigDecimal("1.00"))
                .price(new BigDecimal("60.00"))
                .isBesst(false)
                .isNewMarket(true)
                .build());
        entityManager.flush();
        entityManager.clear();

        List<Stock> all = allStocks.all();

        assertThat(all).hasSize(2);
    }
}
