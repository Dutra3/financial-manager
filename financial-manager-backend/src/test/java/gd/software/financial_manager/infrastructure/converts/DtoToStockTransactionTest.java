package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.infrastructure.dtos.StockTransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class DtoToStockTransactionTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID STOCK_ID = UUID.randomUUID();
    private static final BigDecimal QUANTITY = new BigDecimal("50.00");
    private static final BigDecimal PRICE = new BigDecimal("100.50");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert() {
        StockTransactionDTO stockTransactionDTO = new StockTransactionDTO(ID, STOCK_ID, QUANTITY, PRICE, TRANSACTION_DATE);
        StockTransaction stockTransaction = DtoToStockTransaction.convert(stockTransactionDTO);

        assertThat(stockTransaction).isNotNull();
        assertThat(stockTransaction.id()).isEqualTo(ID);
        assertThat(stockTransaction.stock().id()).isEqualTo(STOCK_ID);
        assertThat(stockTransaction.quantity()).isEqualTo(QUANTITY);
        assertThat(stockTransaction.price()).isEqualTo(PRICE);
        assertThat(stockTransaction.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
