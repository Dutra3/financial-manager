package gd.software.financial_manager.domain.usecase.transaction;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRecurringTransactionsTest {

    @Mock
    private AllTransactions allTransactions;

    @Mock
    private AllCategories allCategories;

    @InjectMocks
    private CreateRecurringTransactions createRecurringTransactions;

    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    private Category category() {
        return new Category(CATEGORY_ID, "Subscription", CategoryType.DEBIT);
    }

    @Test
    void should_create_monthly_recurring_transactions() {
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.of(category()));
        when(allTransactions.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        List<Transaction> created = createRecurringTransactions.use("Netflix", "Streaming",
                new BigDecimal("39.90"), LocalDate.of(2025, 1, 15), CATEGORY_ID, null, USER_ID, 3, "MONTHLY");

        assertThat(created).hasSize(3);
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(allTransactions, times(3)).save(captor.capture());
        List<Transaction> saved = captor.getAllValues();

        assertThat(saved.get(0).paymentDate()).isEqualTo(LocalDate.of(2025, 1, 15));
        assertThat(saved.get(1).paymentDate()).isEqualTo(LocalDate.of(2025, 2, 15));
        assertThat(saved.get(2).paymentDate()).isEqualTo(LocalDate.of(2025, 3, 15));
        assertThat(saved.get(0).userId()).isEqualTo(USER_ID);
        assertThat(saved.get(0).category().id()).isEqualTo(CATEGORY_ID);
    }

    @Test
    void should_create_weekly_recurring_transactions() {
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.of(category()));
        when(allTransactions.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        List<Transaction> created = createRecurringTransactions.use("Gym", null,
                new BigDecimal("10.00"), LocalDate.of(2025, 1, 6), CATEGORY_ID, null, USER_ID, 2, "WEEKLY");

        assertThat(created).hasSize(2);
        assertThat(created.get(1).paymentDate()).isEqualTo(LocalDate.of(2025, 1, 13));
    }

    @Test
    void should_throw_when_category_not_found() {
        when(allCategories.by(CATEGORY_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createRecurringTransactions.use("X", null, new BigDecimal("10.00"),
                LocalDate.now(), CATEGORY_ID, null, USER_ID, 2, "MONTHLY"))
                .isInstanceOf(EntityNotFoundException.class);
        verifyNoInteractions(allTransactions);
    }
}
