package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Bond;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllBondsPersistent.class)
class AllBondsPersistentTest {

    @Autowired
    private AllBondsPersistent allBonds;

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
}
