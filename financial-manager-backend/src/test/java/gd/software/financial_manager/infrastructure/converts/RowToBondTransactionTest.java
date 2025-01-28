package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;
import gd.software.financial_manager.infrastructure.persistence.relational.BondTransactionRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RowToBondTransactionTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID BOND_ID = UUID.randomUUID();
    private static final BondRow BOND = BondRow.builder().id(BOND_ID).build();
    private static final BigDecimal QUANTITY = new BigDecimal("21.00");
    private static final BigDecimal PRICE = new BigDecimal("89.32");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert() {
        BondTransactionRow row = BondTransactionRow.builder()
                .id(ID)
                .bond(BOND)
                .quantity(QUANTITY)
                .price(PRICE)
                .transactionDate(TRANSACTION_DATE)
                .build();
        BondTransaction bondTransaction = RowToBondTransaction.convert(row);

        assertThat(bondTransaction).isNotNull();
        assertThat(bondTransaction.id()).isEqualTo(ID);
        assertThat(bondTransaction.bond().id()).isEqualTo(BOND_ID);
        assertThat(bondTransaction.quantity()).isEqualTo(QUANTITY);
        assertThat(bondTransaction.price()).isEqualTo(PRICE);
        assertThat(bondTransaction.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
