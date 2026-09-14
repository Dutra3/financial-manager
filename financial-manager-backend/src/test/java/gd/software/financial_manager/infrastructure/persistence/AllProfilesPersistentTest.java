package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllProfilesPersistent.class)
class AllProfilesPersistentTest {

    @Autowired
    private AllProfilesPersistent allProfiles;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_save_profile_and_return_domain() {
        entityManager.persist(UserRow.builder()
                .email("profile@test.com")
                .password("encoded")
                .build());
        entityManager.flush();

        Profile profile = new Profile(null, "Gabriel Dutra", "Developer",
                new BigDecimal("5500.00"), 7, new BigDecimal("1200.00"), new BigDecimal("10000.00"));

        Profile saved = allProfiles.save(profile);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.name()).isEqualTo("Gabriel Dutra");
        assertThat(saved.netSalary()).isEqualByComparingTo("5500.00");
        assertThat(saved.financialGoal()).isEqualByComparingTo("10000.00");
    }
}
