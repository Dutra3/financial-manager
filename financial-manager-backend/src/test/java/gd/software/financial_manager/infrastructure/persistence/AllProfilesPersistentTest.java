package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.persistence.relational.ProfileRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

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

    @Test
    void should_find_profile_by_id() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("profile@test.com")
                .password("encoded")
                .build());
        ProfileRow row = entityManager.persist(ProfileRow.builder()
                .name("Gabriel Dutra")
                .profession("Developer")
                .netSalary(new BigDecimal("5500.00"))
                .payday(7)
                .initialBalance(new BigDecimal("1200.00"))
                .financialGoal(new BigDecimal("9999.00"))
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        Optional<Profile> found = allProfiles.byId(row.getId());

        assertThat(found).isPresent();
        assertThat(found.get().name()).isEqualTo("Gabriel Dutra");
        assertThat(found.get().profession()).isEqualTo("Developer");
        assertThat(found.get().netSalary()).isEqualByComparingTo("5500.00");
    }

    @Test
    void should_return_empty_when_profile_not_found() {
        Optional<Profile> found = allProfiles.byId(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void should_preserve_user_when_updating_existing_profile() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("update@test.com")
                .password("encoded")
                .build());
        ProfileRow existing = entityManager.persist(ProfileRow.builder()
                .name("Old Name")
                .profession("Old Job")
                .netSalary(new BigDecimal("3000.00"))
                .payday(5)
                .initialBalance(new BigDecimal("500.00"))
                .financialGoal(new BigDecimal("5000.00"))
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        Profile update = new Profile(existing.getId(), "New Name", "New Job",
                new BigDecimal("5500.00"), 10, new BigDecimal("1200.00"), new BigDecimal("9000.00"));
        allProfiles.save(update);

        ProfileRow updated = entityManager.find(ProfileRow.class, existing.getId());
        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getUser().getId()).isEqualTo(user.getId());
    }
}
