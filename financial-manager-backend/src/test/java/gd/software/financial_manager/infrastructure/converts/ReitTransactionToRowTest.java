package gd.software.financial_manager.infrastructure.converts;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitTransactionRow;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReitTransactionToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID REIT_ID = UUID.randomUUID();
    private static final Reit REIT = new Reit(REIT_ID);
    private static final BigDecimal QUANTITY = new BigDecimal("10.50");
    private static final BigDecimal PRICE = new BigDecimal("150.75");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert_reit_transaction_to_row() {
        ReitTransaction reitTransaction = new ReitTransaction(ID, REIT, QUANTITY, PRICE, TRANSACTION_DATE);
        ReitTransactionRow row = ReitTransactionToRow.convert(reitTransaction);

        assertThat(row).isNotNull();
        assertThat(row.getId()).isEqualTo(ID);
        assertThat(row.getReit().getId()).isEqualTo(REIT.id());
        assertThat(row.getQuantity()).isEqualTo(QUANTITY);
        assertThat(row.getPrice()).isEqualTo(PRICE);
        assertThat(row.getTransactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
