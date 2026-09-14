package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;
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
@Import(AllReitsPersistent.class)
class AllReitsPersistentTest {

    @Autowired
    private AllReitsPersistent allReits;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_find_reit_by_ticker() {
        entityManager.persist(ReitRow.builder()
                .name("MXRF11")
                .ticker("MXRF11")
                .description("Real Estate Fund")
                .type("REIT")
                .industrySegment("Real Estate")
                .price(new BigDecimal("120.00"))
                .build());
        entityManager.flush();
        entityManager.clear();

        Optional<Reit> found = allReits.findReitByTicker("MXRF11");

        assertThat(found).isPresent();
        assertThat(found.get().name()).isEqualTo("MXRF11");
        assertThat(found.get().price()).isEqualByComparingTo("120.00");
    }

    @Test
    void should_return_empty_when_ticker_not_found() {
        Optional<Reit> found = allReits.findReitByTicker("NONEXISTENT");

        assertThat(found).isEmpty();
    }

    @Test
    void should_find_reit_by_id() {
        ReitRow row = entityManager.persist(ReitRow.builder()
                .name("MXRF11")
                .ticker("MXRF11")
                .description("Real Estate Fund")
                .type("REIT")
                .industrySegment("Real Estate")
                .price(new BigDecimal("120.00"))
                .build());
        entityManager.flush();
        entityManager.clear();

        Optional<Reit> found = allReits.by(row.getId());

        assertThat(found).isPresent();
        assertThat(found.get().ticker()).isEqualTo("MXRF11");
    }

    @Test
    void should_return_empty_when_reit_id_not_found() {
        Optional<Reit> found = allReits.by(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void should_find_all_reits() {
        entityManager.persist(ReitRow.builder()
                .name("MXRF11")
                .ticker("MXRF11")
                .description("Real Estate Fund")
                .type("REIT")
                .industrySegment("Real Estate")
                .price(new BigDecimal("120.00"))
                .build());
        entityManager.persist(ReitRow.builder()
                .name("HGLG11")
                .ticker("HGLG11")
                .description("Logistics Fund")
                .type("REIT")
                .industrySegment("Logistics")
                .price(new BigDecimal("160.00"))
                .build());
        entityManager.flush();
        entityManager.clear();

        List<Reit> all = allReits.all();

        assertThat(all).hasSize(2);
    }
}
