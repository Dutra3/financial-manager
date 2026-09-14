package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllReitTransactionsPersistent.class)
class AllReitTransactionsPersistentTest {

    @Autowired
    private AllReitTransactionsPersistent allReitTransactions;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_save_reit_transaction_and_return_domain() {
        ReitRow reitRow = entityManager.persist(ReitRow.builder()
                .name("MXRF11")
                .ticker("MXRF11")
                .description("Real Estate Fund")
                .type("REIT")
                .industrySegment("Real Estate")
                .price(new BigDecimal("120.00"))
                .build());
        Reit reit = new Reit(reitRow.getId(), "MXRF11", "MXRF11", "Real Estate Fund", "REIT", "Real Estate",
                new BigDecimal("120.00"));
        ReitTransaction transaction = new ReitTransaction(null, reit, new BigDecimal("50"),
                new BigDecimal("120.00"), LocalDate.of(2025, 1, 25));

        ReitTransaction saved = allReitTransactions.save(transaction);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.reit().ticker()).isEqualTo("MXRF11");
        assertThat(saved.quantity()).isEqualByComparingTo("50");
    }
}
