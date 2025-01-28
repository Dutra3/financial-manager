package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.BondTransactionRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BondTransactionToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID BOND_ID = UUID.randomUUID();
    private static final Bond BOND = new Bond(BOND_ID);
    private static final BigDecimal QUANTITY = new BigDecimal("21.00");
    private static final BigDecimal PRICE = new BigDecimal("89.32");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert() {
        BondTransaction bondTransaction = new BondTransaction(ID, BOND, QUANTITY, PRICE, TRANSACTION_DATE);
        BondTransactionRow row = BondTransactionToRow.convert(bondTransaction);

        assertThat(row).isNotNull();
        assertThat(row.getId()).isEqualTo(ID);
        assertThat(row.getBond().getId()).isEqualTo(BOND_ID);
        assertThat(row.getQuantity()).isEqualTo(QUANTITY);
        assertThat(row.getPrice()).isEqualTo(PRICE);
        assertThat(row.getTransactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
