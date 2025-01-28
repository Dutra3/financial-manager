package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.infrastructure.dtos.BondTransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BondTransactionToDTOTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID BOND_ID = UUID.randomUUID();
    private static final Bond BOND = new Bond(BOND_ID);
    private static final BigDecimal QUANTITY = new BigDecimal("21.00");
    private static final BigDecimal PRICE = new BigDecimal("89.32");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert() {
        BondTransaction bondTransaction = new BondTransaction(ID, BOND, QUANTITY, PRICE, TRANSACTION_DATE);
        BondTransactionDTO dto = BondTransactionToDTO.convert(bondTransaction);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(ID);
        assertThat(dto.bondId()).isEqualTo(BOND_ID);
        assertThat(dto.quantity()).isEqualTo(QUANTITY);
        assertThat(dto.price()).isEqualTo(PRICE);
        assertThat(dto.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
