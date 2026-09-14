package gd.software.financial_manager.domain.usecase.bond;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllBondTransactions;
import gd.software.financial_manager.domain.usecase.collections.AllBonds;
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
class BondUseCasesTest {

    @Mock
    private AllBondTransactions allBondTransactions;

    @Mock
    private AllBonds allBonds;

    @InjectMocks
    private CreateBondTransaction createBondTransaction;

    @InjectMocks
    private FetchBond fetchBond;

    private static final UUID BOND_ID = UUID.randomUUID();
    private static final UUID TRANSACTION_ID = UUID.randomUUID();

    @Test
    void createBondTransaction_should_delegate_to_allBondTransactions_save() {
        Bond bond = new Bond(BOND_ID, "Tesouro Selic", "Government Bond", "Public", "Treasury", new BigDecimal("100.50"));
        BondTransaction input = new BondTransaction(null, bond, new BigDecimal("10"),
                new BigDecimal("100.50"), LocalDate.of(2025, 1, 15));
        BondTransaction saved = new BondTransaction(TRANSACTION_ID, bond, new BigDecimal("10"),
                new BigDecimal("100.50"), LocalDate.of(2025, 1, 15));
        when(allBondTransactions.save(input)).thenReturn(saved);

        BondTransaction result = createBondTransaction.use(input);

        assertThat(result).isEqualTo(saved);
        verify(allBondTransactions).save(input);
    }

    @Test
    void fetchBond_by_should_return_bond_when_found() throws Exception {
        Bond bond = new Bond(BOND_ID, "Tesouro Selic", "Government Bond", "Public", "Treasury", new BigDecimal("100.50"));
        when(allBonds.by(BOND_ID)).thenReturn(Optional.of(bond));

        Bond result = fetchBond.by(BOND_ID);

        assertThat(result).isEqualTo(bond);
        verify(allBonds).by(BOND_ID);
    }

    @Test
    void fetchBond_by_should_throw_when_not_found() {
        when(allBonds.by(BOND_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fetchBond.by(BOND_ID))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("cant_find_bond_with_id");
    }

    @Test
    void fetchBond_all_should_delegate_to_allBonds_all() {
        List<Bond> bonds = List.of(
                new Bond(BOND_ID, "Tesouro Selic", "Government Bond", "Public", "Treasury", new BigDecimal("100.50")));
        when(allBonds.all()).thenReturn(bonds);

        List<Bond> result = fetchBond.all();

        assertThat(result).isEqualTo(bonds);
        verify(allBonds).all();
    }
}
