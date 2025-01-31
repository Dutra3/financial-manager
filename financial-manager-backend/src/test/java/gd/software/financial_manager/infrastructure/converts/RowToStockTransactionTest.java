package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.StockRow;
import gd.software.financial_manager.infrastructure.persistence.relational.StockTransactionRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class RowToStockTransactionTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID STOCK_ID = UUID.randomUUID();
    private static final BigDecimal QUANTITY = new BigDecimal("50.00");
    private static final BigDecimal PRICE = new BigDecimal("100.50");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert_stock_transaction_row_to_entity() {
        StockRow stockRow = StockRow.builder().id(STOCK_ID).build();
            StockTransactionRow stockTransactionRow = StockTransactionRow.builder()
                .id(ID)
                .stock(stockRow)
                .quantity(QUANTITY)
                .price(PRICE)
                .transactionDate(TRANSACTION_DATE)
                .build();

        StockTransaction stockTransaction = RowToStockTransaction.convert(stockTransactionRow);

        assertThat(stockTransaction).isNotNull();
        assertThat(stockTransaction.id()).isEqualTo(ID);
        assertThat(stockTransaction.stock().id()).isEqualTo(STOCK_ID);
        assertThat(stockTransaction.quantity()).isEqualTo(QUANTITY);
        assertThat(stockTransaction.price()).isEqualTo(PRICE);
        assertThat(stockTransaction.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
