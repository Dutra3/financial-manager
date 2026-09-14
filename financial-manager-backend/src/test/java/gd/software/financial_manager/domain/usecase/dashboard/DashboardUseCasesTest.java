package gd.software.financial_manager.domain.usecase.dashboard;

import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Installment;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import gd.software.financial_manager.domain.usecase.credit.FetchCredit;
import gd.software.financial_manager.domain.usecase.debit.FetchDebit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardUseCasesTest {

    @Mock
    private AllTransactions allTransactions;

    @InjectMocks
    private FetchDebit fetchDebit;

    @InjectMocks
    private FetchCredit fetchCredit;

    private static final UUID USER_ID = UUID.randomUUID();

    @Test
    void fetchDebit_all_should_delegate_with_DEBIT_type() {
        List<Installment> installments = List.of(
                new Installment(new BigDecimal("50.00")),
                new Installment(new BigDecimal("100.00")));
        when(allTransactions.byUserIdAndType(USER_ID, CategoryType.DEBIT)).thenReturn(installments);

        List<Installment> result = fetchDebit.all(USER_ID);

        assertThat(result).isEqualTo(installments);
        verify(allTransactions).byUserIdAndType(USER_ID, CategoryType.DEBIT);
    }

    @Test
    void fetchCredit_all_should_delegate_with_CREDIT_type() {
        List<Installment> installments = List.of(
                new Installment(new BigDecimal("5000.00")));
        when(allTransactions.byUserIdAndType(USER_ID, CategoryType.CREDIT)).thenReturn(installments);

        List<Installment> result = fetchCredit.all(USER_ID);

        assertThat(result).isEqualTo(installments);
        verify(allTransactions).byUserIdAndType(USER_ID, CategoryType.CREDIT);
    }

    @Test
    void fetchDebit_all_should_return_empty_when_no_debits() {
        when(allTransactions.byUserIdAndType(USER_ID, CategoryType.DEBIT)).thenReturn(List.of());

        List<Installment> result = fetchDebit.all(USER_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void fetchCredit_all_should_return_empty_when_no_credits() {
        when(allTransactions.byUserIdAndType(USER_ID, CategoryType.CREDIT)).thenReturn(List.of());

        List<Installment> result = fetchCredit.all(USER_ID);

        assertThat(result).isEmpty();
    }
}
