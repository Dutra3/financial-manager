package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReitRepositoryTest {

    @Autowired
    private ReitRepository reitRepository;

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

        Optional<ReitRow> found = reitRepository.findByTicker("MXRF11");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("MXRF11");
        assertThat(found.get().getPrice()).isEqualByComparingTo("120.00");
    }

    @Test
    void should_return_empty_when_ticker_not_found() {
        Optional<ReitRow> found = reitRepository.findByTicker("NONEXISTENT");

        assertThat(found).isEmpty();
    }
}
