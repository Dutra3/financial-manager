package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllEtfTransactionsPersistent.class)
class AllEtfTransactionsPersistentTest {

    @Autowired
    private AllEtfTransactionsPersistent allEtfTransactions;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_save_etf_transaction_and_return_domain() {
        EtfRow etfRow = entityManager.persist(EtfRow.builder()
                .name("IVVB11")
                .ticker("IVVB11")
                .description("S&P 500 ETF")
                .type("ETF")
                .industrySegment("International")
                .price(new BigDecimal("80.00"))
                .build());
        Etf etf = new Etf(etfRow.getId(), "IVVB11", "IVVB11", "S&P 500 ETF", "ETF", "International",
                new BigDecimal("80.00"));
        EtfTransaction transaction = new EtfTransaction(null, etf, new BigDecimal("20"),
                new BigDecimal("80.00"), LocalDate.of(2025, 2, 1));

        EtfTransaction saved = allEtfTransactions.save(transaction);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.etf().ticker()).isEqualTo("IVVB11");
        assertThat(saved.quantity()).isEqualByComparingTo("20");
    }
}
