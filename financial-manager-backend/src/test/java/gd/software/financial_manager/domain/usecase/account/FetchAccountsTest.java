package gd.software.financial_manager.domain.usecase.account;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllAccounts;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchAccountsTest {

    @Mock
    private AllAccounts allAccounts;

    @Mock
    private AllTransactions allTransactions;

    @InjectMocks
    private FetchAccounts fetchAccounts;

    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID ACC_A = UUID.randomUUID();
    private static final UUID ACC_B = UUID.randomUUID();

    private Transaction tx(String amount, CategoryType type, UUID accountId) {
        return new Transaction(null, "T", null, new BigDecimal(amount), LocalDate.now(),
                new Category(UUID.randomUUID(), "Cat", type), USER_ID, accountId);
    }

    @Test
    void should_compute_credit_minus_debit_per_account() {
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of(
                tx("1000.00", CategoryType.CREDIT, ACC_A),
                tx("300.00", CategoryType.DEBIT, ACC_A),
                tx("50.00", CategoryType.CREDIT, ACC_B),
                tx("200.00", CategoryType.DEBIT, null)));

        Map<UUID, BigDecimal> deltas = fetchAccounts.balanceDeltaByAccount(USER_ID);

        assertThat(deltas.get(ACC_A)).isEqualByComparingTo("700.00");
        assertThat(deltas.get(ACC_B)).isEqualByComparingTo("50.00");
        assertThat(deltas).hasSize(2);
    }

    @Test
    void should_return_empty_map_without_transactions() {
        when(allTransactions.allBy(USER_ID)).thenReturn(List.of());

        assertThat(fetchAccounts.balanceDeltaByAccount(USER_ID)).isEmpty();
    }
}
