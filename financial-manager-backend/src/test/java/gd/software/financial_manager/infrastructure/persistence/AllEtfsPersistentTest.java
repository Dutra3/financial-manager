package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfRow;
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
@Import(AllEtfsPersistent.class)
class AllEtfsPersistentTest {

    @Autowired
    private AllEtfsPersistent allEtfs;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_find_etf_by_id() {
        EtfRow row = entityManager.persist(EtfRow.builder()
                .name("IVVB11")
                .ticker("IVVB11")
                .description("S&P 500 ETF")
                .type("ETF")
                .industrySegment("International")
                .price(new BigDecimal("80.00"))
                .build());
        entityManager.flush();
        entityManager.clear();

        Optional<Etf> found = allEtfs.by(row.getId());

        assertThat(found).isPresent();
        assertThat(found.get().ticker()).isEqualTo("IVVB11");
        assertThat(found.get().price()).isEqualByComparingTo("80.00");
    }

    @Test
    void should_return_empty_when_etf_id_not_found() {
        Optional<Etf> found = allEtfs.by(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void should_find_all_etfs() {
        entityManager.persist(EtfRow.builder()
                .name("IVVB11")
                .ticker("IVVB11")
                .description("S&P 500 ETF")
                .type("ETF")
                .industrySegment("International")
                .price(new BigDecimal("80.00"))
                .build());
        entityManager.persist(EtfRow.builder()
                .name("HASH11")
                .ticker("HASH11")
                .description("Crypto ETF")
                .type("ETF")
                .industrySegment("Crypto")
                .price(new BigDecimal("15.00"))
                .build());
        entityManager.flush();
        entityManager.clear();

        List<Etf> all = allEtfs.all();

        assertThat(all).hasSize(2);
    }

    @Test
    void should_return_empty_list_when_no_etfs() {
        List<Etf> all = allEtfs.all();

        assertThat(all).isEmpty();
    }
}
