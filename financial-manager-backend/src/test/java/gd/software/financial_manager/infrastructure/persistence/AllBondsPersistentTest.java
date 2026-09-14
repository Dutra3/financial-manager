package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;
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
@Import(AllBondsPersistent.class)
class AllBondsPersistentTest {

    @Autowired
    private AllBondsPersistent allBonds;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_return_empty_when_bond_not_found() {
        Optional<Bond> found = allBonds.by(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void should_return_empty_list_when_no_bonds() {
        List<Bond> all = allBonds.all();

        assertThat(all).isEmpty();
    }

    @Test
    void should_find_bond_by_id() {
        BondRow row = entityManager.persist(BondRow.builder()
                .name("Tesouro Direto")
                .description("Government bond")
                .type("Fixed")
                .industrySegment("Government")
                .price(new BigDecimal("1000.00"))
                .build());
        entityManager.flush();
        entityManager.clear();

        Optional<Bond> found = allBonds.by(row.getId());

        assertThat(found).isPresent();
        assertThat(found.get().name()).isEqualTo("Tesouro Direto");
    }

    @Test
    void should_find_all_bonds() {
        entityManager.persist(BondRow.builder()
                .name("Tesouro Direto")
                .description("Government bond")
                .type("Fixed")
                .industrySegment("Government")
                .price(new BigDecimal("1000.00"))
                .build());
        entityManager.persist(BondRow.builder()
                .name("CDB Banco X")
                .description("Bank bond")
                .type("Floating")
                .industrySegment("Banking")
                .price(new BigDecimal("500.00"))
                .build());
        entityManager.flush();
        entityManager.clear();

        List<Bond> all = allBonds.all();

        assertThat(all).hasSize(2);
        assertThat(all).extracting(Bond::name).containsExactlyInAnyOrder("Tesouro Direto", "CDB Banco X");
    }
}
