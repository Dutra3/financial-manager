package gd.software.financial_manager.domain.usecase.reit;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllReitTransactions;
import gd.software.financial_manager.domain.usecase.collections.AllReits;
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
class ReitUseCasesTest {

    @Mock
    private AllReitTransactions allReitTransactions;

    @Mock
    private AllReits allReits;

    @InjectMocks
    private CreateReitTransaction createReitTransaction;

    @InjectMocks
    private FetchReit fetchReit;

    private static final UUID REIT_ID = UUID.randomUUID();
    private static final UUID TRANSACTION_ID = UUID.randomUUID();

    @Test
    void createReitTransaction_should_delegate_to_allReitTransactions_save() {
        Reit reit = new Reit(REIT_ID, "MXRF11", "MXRF11", "Real Estate Fund", "REIT", "Real Estate",
                new BigDecimal("120.00"));
        ReitTransaction input = new ReitTransaction(null, reit, new BigDecimal("50"),
                new BigDecimal("120.00"), LocalDate.of(2025, 1, 25));
        ReitTransaction saved = new ReitTransaction(TRANSACTION_ID, reit, new BigDecimal("50"),
                new BigDecimal("120.00"), LocalDate.of(2025, 1, 25));
        when(allReitTransactions.save(input)).thenReturn(saved);

        ReitTransaction result = createReitTransaction.use(input);

        assertThat(result).isEqualTo(saved);
        verify(allReitTransactions).save(input);
    }

    @Test
    void fetchReit_by_should_return_reit_when_found() throws Exception {
        Reit reit = new Reit(REIT_ID, "MXRF11", "MXRF11", "Real Estate Fund", "REIT", "Real Estate",
                new BigDecimal("120.00"));
        when(allReits.by(REIT_ID)).thenReturn(Optional.of(reit));

        Reit result = fetchReit.by(REIT_ID);

        assertThat(result).isEqualTo(reit);
        verify(allReits).by(REIT_ID);
    }

    @Test
    void fetchReit_by_should_throw_when_not_found() {
        when(allReits.by(REIT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fetchReit.by(REIT_ID))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("cant_find_reit_with_id");
    }

    @Test
    void fetchReit_all_should_delegate_to_allReits_all() {
        List<Reit> reits = List.of(
                new Reit(REIT_ID, "MXRF11", "MXRF11", "Real Estate Fund", "REIT", "Real Estate",
                        new BigDecimal("120.00")));
        when(allReits.all()).thenReturn(reits);

        List<Reit> result = fetchReit.all();

        assertThat(result).isEqualTo(reits);
        verify(allReits).all();
    }
}
