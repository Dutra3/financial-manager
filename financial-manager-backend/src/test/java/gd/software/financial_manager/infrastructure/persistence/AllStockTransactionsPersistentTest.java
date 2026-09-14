package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllStockTransactionsPersistent.class)
class AllStockTransactionsPersistentTest {

    @Autowired
    private AllStockTransactionsPersistent allStockTransactions;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_save_stock_transaction_and_return_domain() {
        StockRow stockRow = entityManager.persist(StockRow.builder()
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
        Stock stock = new Stock(stockRow.getId(), "Petrobras", "PETR4", "Oil company", "Stock", "Energy",
                new BigDecimal("0.80"), new BigDecimal("35.50"), null, null, null, null, true, true);
        StockTransaction transaction = new StockTransaction(null, stock, new BigDecimal("100"),
                new BigDecimal("35.50"), LocalDate.of(2025, 1, 20));

        StockTransaction saved = allStockTransactions.save(transaction);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.stock().ticker()).isEqualTo("PETR4");
        assertThat(saved.quantity()).isEqualByComparingTo("100");
    }
}
