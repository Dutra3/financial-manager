package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitTransactionRow;
import org.junit.jupiter.api.Test;

class RowToReitTransactionTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID REIT_ID = UUID.randomUUID();
    private static final ReitRow REIT_ROW = ReitRow.builder().id(REIT_ID).build();
    private static final BigDecimal QUANTITY = new BigDecimal("18.50");
    private static final BigDecimal PRICE = new BigDecimal("150.75");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert_row_to_reit_transaction() {
        ReitTransactionRow reitTransactionRow = ReitTransactionRow.builder()
                .id(ID)
                .reit(REIT_ROW)
                .quantity(QUANTITY)
                .price(PRICE)
                .transactionDate(TRANSACTION_DATE)
                .build();
        ReitTransaction reitTransaction = RowToReitTransaction.convert(reitTransactionRow);

        assertThat(reitTransaction).isNotNull();
        assertThat(reitTransaction.id()).isEqualTo(ID);
        assertThat(reitTransaction.reit().id()).isEqualTo(REIT_ID);
        assertThat(reitTransaction.quantity()).isEqualTo(QUANTITY);
        assertThat(reitTransaction.price()).isEqualTo(PRICE);
        assertThat(reitTransaction.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
