package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.infrastructure.dtos.BondTransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DtoToBondTransactionTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID BOND_ID = UUID.randomUUID();
    private static final BigDecimal QUANTITY = new BigDecimal("21.00");
    private static final BigDecimal PRICE = new BigDecimal("89.32");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert() {
        BondTransactionDTO dto = new BondTransactionDTO(ID, BOND_ID, QUANTITY, PRICE, TRANSACTION_DATE);
        BondTransaction bondTransaction = DtoToBondTransaction.convert(dto);

        assertThat(bondTransaction).isNotNull();
        assertThat(bondTransaction.id()).isEqualTo(ID);
        assertThat(bondTransaction.bond().id()).isEqualTo(BOND_ID);
        assertThat(bondTransaction.quantity()).isEqualTo(QUANTITY);
        assertThat(bondTransaction.price()).isEqualTo(PRICE);
        assertThat(bondTransaction.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
