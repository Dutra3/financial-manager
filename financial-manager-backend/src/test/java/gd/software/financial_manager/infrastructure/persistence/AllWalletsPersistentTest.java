package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import gd.software.financial_manager.infrastructure.persistence.relational.WalletRow;
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
@Import(AllWalletsPersistent.class)
class AllWalletsPersistentTest {

    @Autowired
    private AllWalletsPersistent allWallets;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_find_wallet_by_id() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("wallet@test.com")
                .password("encoded")
                .build());
        WalletRow row = entityManager.persist(WalletRow.builder()
                .amount(new BigDecimal("9999.00"))
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        Optional<Wallet> found = allWallets.by(row.getId());

        assertThat(found).isPresent();
        assertThat(found.get().id()).isEqualTo(row.getId());
        assertThat(found.get().totalAmount()).isEqualByComparingTo("9999.00");
    }

    @Test
    void should_return_empty_when_wallet_not_found() {
        Optional<Wallet> found = allWallets.by(UUID.randomUUID());

        assertThat(found).isEmpty();
    }
}
