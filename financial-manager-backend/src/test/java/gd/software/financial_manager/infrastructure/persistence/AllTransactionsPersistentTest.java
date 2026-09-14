package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Installment;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryTypeRow;
import gd.software.financial_manager.infrastructure.persistence.relational.TransactionRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AllTransactionsPersistent.class)
class AllTransactionsPersistentTest {

    @Autowired
    private AllTransactionsPersistent allTransactions;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_save_transaction_and_return_domain() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("user@test.com")
                .password("encoded")
                .build());
        CategoryRow categoryRow = entityManager.persist(CategoryRow.builder()
                .name("Food")
                .type(CategoryTypeRow.DEBIT)
                .build());
        Category category = new Category(categoryRow.getId(), "Food", CategoryType.DEBIT);
        Transaction transaction = new Transaction(null, "Lunch", "Restaurant",
                new BigDecimal("50.00"), LocalDate.of(2025, 1, 10), category);

        Transaction saved = allTransactions.save(transaction);

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.name()).isEqualTo("Lunch");
        assertThat(saved.amount()).isEqualByComparingTo("50.00");
    }

    @Test
    void should_find_all_transactions_by_user_id() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("user@test.com")
                .password("encoded")
                .build());
        CategoryRow categoryRow = entityManager.persist(CategoryRow.builder()
                .name("Food")
                .type(CategoryTypeRow.DEBIT)
                .build());
        entityManager.persist(TransactionRow.builder()
                .name("Lunch")
                .description("Restaurant")
                .amount(new BigDecimal("50.00"))
                .paymentDate(LocalDate.of(2025, 1, 10))
                .category(categoryRow)
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        List<Transaction> found = allTransactions.allBy(user.getId());

        assertThat(found).hasSize(1);
        assertThat(found.get(0).name()).isEqualTo("Lunch");
    }

    @Test
    void should_return_empty_list_when_no_transactions_for_user() {
        List<Transaction> found = allTransactions.allBy(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void should_remove_transaction_by_id() {
        UserRow user = entityManager.persist(UserRow.builder()
                .email("user@test.com")
                .password("encoded")
                .build());
        CategoryRow categoryRow = entityManager.persist(CategoryRow.builder()
                .name("Food")
                .type(CategoryTypeRow.DEBIT)
                .build());
        TransactionRow row = entityManager.persist(TransactionRow.builder()
                .name("Lunch")
                .description("Restaurant")
                .amount(new BigDecimal("50.00"))
                .paymentDate(LocalDate.of(2025, 1, 10))
                .category(categoryRow)
                .user(user)
                .build());
        entityManager.flush();
        entityManager.clear();

        allTransactions.remove(row.getId());

        assertThat(allTransactions.allBy(user.getId())).isEmpty();
    }

    @Test
    void should_find_installments_by_user_id_and_debit_type() {
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

        List<Installment> debitInstallments = allTransactions.byUserIdAndType(user.getId(), CategoryType.DEBIT);

        assertThat(debitInstallments).hasSize(1);
        assertThat(debitInstallments.get(0).amount()).isEqualByComparingTo("50.00");
    }

    @Test
    void should_return_empty_installments_when_no_matching_type() {
        List<Installment> installments = allTransactions.byUserIdAndType(UUID.randomUUID(), CategoryType.DEBIT);

        assertThat(installments).isEmpty();
    }
}
