package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllBondTransactionsPersistent.class)
class AllBondTransactionsPersistentTest {

    @Autowired
    private AllBondTransactionsPersistent allBondTransactions;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_save_bond_transaction_and_return_domain() {
        BondRow bondRow = entityManager.persist(BondRow.builder()
                .name("Tesouro Selic")
                .description("Government Bond")
                .type("Public")
                .industrySegment("Treasury")
                .price(new BigDecimal("100.50"))
                .build());
        Bond bond = new Bond(bondRow.getId(), "Tesouro Selic", "Government Bond", "Public", "Treasury", new BigDecimal("100.50"));
        BondTransaction transaction = new BondTransaction(null, bond, new BigDecimal("10"),
                new BigDecimal("100.50"), LocalDate.of(2025, 1, 15));

        BondTransaction saved = allBondTransactions.save(transaction);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.bond().name()).isEqualTo("Tesouro Selic");
        assertThat(saved.quantity()).isEqualByComparingTo("10");
    }
}
