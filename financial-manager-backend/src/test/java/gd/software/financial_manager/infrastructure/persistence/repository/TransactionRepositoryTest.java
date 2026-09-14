package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryTypeRow;
import gd.software.financial_manager.infrastructure.persistence.relational.TransactionRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_find_transactions_by_user_id() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("user@test.com")
                .password("encoded")
                .build());
        CategoryRow category = entityManager.persist(CategoryRow.builder()
                .name("Food")
                .type(CategoryTypeRow.DEBIT)
                .build());
        entityManager.persist(TransactionRow.builder()
                .name("Lunch")
                .description("Restaurant")
                .amount(new BigDecimal("50.00"))
                .paymentDate(LocalDate.of(2025, 1, 10))
                .category(category)
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        List<TransactionRow> found = transactionRepository.findByUserId(user.getId());

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getName()).isEqualTo("Lunch");
        assertThat(found.get(0).getAmount()).isEqualByComparingTo("50.00");
    }

    @Test
    void should_return_empty_when_no_transactions_for_user() {
        List<TransactionRow> found = transactionRepository.findByUserId(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void should_find_amounts_by_user_id_and_debit_type() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("debit@test.com")
                .password("encoded")
                .build());
        CategoryRow debitCategory = entityManager.persist(CategoryRow.builder()
                .name("Food")
                .type(CategoryTypeRow.DEBIT)
                .build());
        CategoryRow creditCategory = entityManager.persist(CategoryRow.builder()
                .name("Salary")
                .type(CategoryTypeRow.CREDIT)
                .build());
        entityManager.persist(TransactionRow.builder()
                .name("Lunch")
                .description("Restaurant")
                .amount(new BigDecimal("50.00"))
                .paymentDate(LocalDate.of(2025, 1, 10))
                .category(debitCategory)
                .user(user)
                .build());
        entityManager.persist(TransactionRow.builder()
                .name("Salary")
                .description("Monthly")
                .amount(new BigDecimal("5000.00"))
                .paymentDate(LocalDate.of(2025, 1, 5))
                .category(creditCategory)
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        List<BigDecimal> debitAmounts = transactionRepository.findByUserIdAndType(user.getId(), CategoryTypeRow.DEBIT);

        assertThat(debitAmounts).hasSize(1);
        assertThat(debitAmounts.get(0)).isEqualByComparingTo("50.00");
    }

    @Test
    void should_find_amounts_by_user_id_and_credit_type() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("credit@test.com")
                .password("encoded")
                .build());
        CategoryRow creditCategory = entityManager.persist(CategoryRow.builder()
                .name("Salary")
                .type(CategoryTypeRow.CREDIT)
                .build());
        entityManager.persist(TransactionRow.builder()
                .name("Salary")
                .description("Monthly")
                .amount(new BigDecimal("5000.00"))
                .paymentDate(LocalDate.of(2025, 1, 5))
                .category(creditCategory)
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        List<BigDecimal> creditAmounts = transactionRepository.findByUserIdAndType(user.getId(), CategoryTypeRow.CREDIT);

        assertThat(creditAmounts).hasSize(1);
        assertThat(creditAmounts.get(0)).isEqualByComparingTo("5000.00");
    }

    @Test
    void should_return_empty_when_no_transactions_match_type() {
        List<BigDecimal> amounts = transactionRepository.findByUserIdAndType(UUID.randomUUID(), CategoryTypeRow.DEBIT);

        assertThat(amounts).isEmpty();
    }
}
