package gd.software.financial_manager.domain.usecase.transaction;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.domain.model.AccountType;
import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllAccounts;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
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
class TransferBetweenAccountsTest {

    @Mock
    private AllAccounts allAccounts;

    @Mock
    private AllCategories allCategories;

    @Mock
    private AllTransactions allTransactions;

    @InjectMocks
    private TransferBetweenAccounts transferBetweenAccounts;

    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID FROM_ID = UUID.randomUUID();
    private static final UUID TO_ID = UUID.randomUUID();

    private Account account(UUID id, String name) {
        return new Account(id, name, AccountType.CHECKING, new BigDecimal("1000.00"), USER_ID);
    }

    @Test
    void should_create_debit_and_credit_transactions() {
        when(allAccounts.byId(FROM_ID)).thenReturn(Optional.of(account(FROM_ID, "Checking")));
        when(allAccounts.byId(TO_ID)).thenReturn(Optional.of(account(TO_ID, "Savings")));
        when(allCategories.all()).thenReturn(List.of());
        when(allCategories.save(any(Category.class)))
                .thenAnswer(inv -> new Category(UUID.randomUUID(), "Transfer", inv.getArgument(0, Category.class).type()));
        when(allTransactions.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        transferBetweenAccounts.use(FROM_ID, TO_ID, new BigDecimal("250.00"), LocalDate.of(2025, 2, 1), "Move money");

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(allTransactions, times(2)).save(captor.capture());
        List<Transaction> saved = captor.getAllValues();

        assertThat(saved.get(0).category().type()).isEqualTo(CategoryType.DEBIT);
        assertThat(saved.get(0).accountId()).isEqualTo(FROM_ID);
        assertThat(saved.get(0).userId()).isEqualTo(USER_ID);
        assertThat(saved.get(1).category().type()).isEqualTo(CategoryType.CREDIT);
        assertThat(saved.get(1).accountId()).isEqualTo(TO_ID);
        assertThat(saved.get(1).userId()).isEqualTo(USER_ID);
    }

    @Test
    void should_reject_same_account_transfer() {
        assertThatThrownBy(() -> transferBetweenAccounts.use(FROM_ID, FROM_ID, new BigDecimal("10.00"),
                LocalDate.now(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("same account");
        verifyNoInteractions(allTransactions);
    }

    @Test
    void should_reject_non_positive_amount() {
        assertThatThrownBy(() -> transferBetweenAccounts.use(FROM_ID, TO_ID, BigDecimal.ZERO,
                LocalDate.now(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positive");
        verifyNoInteractions(allTransactions);
    }

    @Test
    void should_reuse_existing_transfer_category() {
        Category existingDebit = new Category(UUID.randomUUID(), "Transfer", CategoryType.DEBIT);
        Category existingCredit = new Category(UUID.randomUUID(), "Transfer", CategoryType.CREDIT);
        when(allAccounts.byId(FROM_ID)).thenReturn(Optional.of(account(FROM_ID, "Checking")));
        when(allAccounts.byId(TO_ID)).thenReturn(Optional.of(account(TO_ID, "Savings")));
        when(allCategories.all()).thenReturn(List.of(existingDebit, existingCredit));
        when(allTransactions.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        transferBetweenAccounts.use(FROM_ID, TO_ID, new BigDecimal("50.00"), LocalDate.now(), null);

        verify(allCategories, never()).save(any());
        verify(allTransactions, times(2)).save(any());
    }
}
