package gd.software.financial_manager.domain.usecase.transaction;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionUseCasesTest {

    @Mock
    private AllTransactions allTransactions;

    @Mock
    private AllCategories allCategories;

    @InjectMocks
    private CreateTransaction createTransaction;

    @InjectMocks
    private FetchTransaction fetchTransaction;

    @InjectMocks
    private DeleteTransaction deleteTransaction;

    private static final UUID TRANSACTION_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void createTransaction_should_save_when_category_exists() {
        Category category = new Category(CATEGORY_ID, "Food", CategoryType.DEBIT);
        Transaction input = new Transaction(null, "Lunch", "Restaurant",
                new BigDecimal("50.00"), LocalDate.of(2025, 1, 10), category);
        Transaction saved = new Transaction(TRANSACTION_ID, "Lunch", "Restaurant",
                new BigDecimal("50.00"), LocalDate.of(2025, 1, 10), category);
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(allTransactions.save(input)).thenReturn(saved);

        Transaction result = createTransaction.use(input);

        assertThat(result).isEqualTo(saved);
        verify(allCategories).by(CATEGORY_ID);
        verify(allTransactions).save(input);
    }

    @Test
    void createTransaction_should_throw_when_category_not_found() {
        Category category = new Category(CATEGORY_ID, "Food", CategoryType.DEBIT);
        Transaction input = new Transaction(null, "Lunch", "Restaurant",
                new BigDecimal("50.00"), LocalDate.of(2025, 1, 10), category);
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createTransaction.use(input))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("can not found category");
        verify(allTransactions, never()).save(any());
    }

    @Test
    void fetchTransaction_all_should_delegate_to_allTransactions_allBy() {
        Category category = new Category(CATEGORY_ID, "Food", CategoryType.DEBIT);
        List<Transaction> transactions = List.of(
                new Transaction(TRANSACTION_ID, "Lunch", "Restaurant",
                        new BigDecimal("50.00"), LocalDate.of(2025, 1, 10), category));
        when(allTransactions.allBy(USER_ID)).thenReturn(transactions);

        List<Transaction> result = fetchTransaction.all(USER_ID);

        assertThat(result).isEqualTo(transactions);
        verify(allTransactions).allBy(USER_ID);
    }

    @Test
    void deleteTransaction_should_delegate_to_allTransactions_remove() {
        deleteTransaction.remove(TRANSACTION_ID);

        verify(allTransactions).remove(TRANSACTION_ID);
    }
}
