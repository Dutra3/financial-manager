package gd.software.financial_manager.domain.usecase.goal;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllProfiles;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrentBalanceTest {

    @Mock
    private AllProfiles allProfiles;

    @Mock
    private AllTransactions allTransactions;

    @InjectMocks
    private CurrentBalance currentBalance;

    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void currentBalance_should_calculate_initialBalance_plus_credits_minus_debits() {
        Profile profile = new Profile(UUID.randomUUID(), "Test", "Dev",
                new BigDecimal("1000.00"), 5, new BigDecimal("1000.00"), new BigDecimal("5000.00"));
        when(allProfiles.byId(USER_ID)).thenReturn(Optional.of(profile));

        Category creditCat = new Category(CATEGORY_ID, "Salary", CategoryType.CREDIT);
        Category debitCat = new Category(UUID.randomUUID(), "Food", CategoryType.DEBIT);
        List<Transaction> transactions = List.of(
                new Transaction(UUID.randomUUID(), "Salary", "", new BigDecimal("3000.00"), LocalDate.now(), creditCat),
                new Transaction(UUID.randomUUID(), "Groceries", "", new BigDecimal("500.00"), LocalDate.now(), debitCat),
                new Transaction(UUID.randomUUID(), "Bonus", "", new BigDecimal("200.00"), LocalDate.now(), creditCat)
        );
        when(allTransactions.allBy(USER_ID)).thenReturn(transactions);

        BigDecimal balance = currentBalance.forUser(USER_ID);

        assertThat(balance).isEqualByComparingTo("3700.00");
    }

    @Test
    void currentBalance_should_return_initialBalance_when_no_transactions() {
        Profile profile = new Profile(UUID.randomUUID(), "Test", "Dev",
                new BigDecimal("2500.00"), 5, new BigDecimal("2500.00"), new BigDecimal("10000.00"));
        when(allProfiles.byId(USER_ID)).thenReturn(Optional.of(profile));
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of());

        BigDecimal balance = currentBalance.forUser(USER_ID);

        assertThat(balance).isEqualByComparingTo("2500.00");
    }

    @Test
    void currentBalance_should_throw_when_profile_not_found() {
        when(allProfiles.byId(USER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> currentBalance.forUser(USER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Profile not found");
    }
}
