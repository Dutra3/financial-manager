package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.infrastructure.dtos.StockTransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class StockTransactionToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID STOCK_ID = UUID.randomUUID();
    private static final BigDecimal QUANTITY = new BigDecimal("50.00");
    private static final BigDecimal PRICE = new BigDecimal("100.50");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert() {
        Stock stock = new Stock(STOCK_ID, "Banco do Brasil", "BBSA3", "Description", "Banco", "Bancario", new BigDecimal("1.00"), new BigDecimal("22.00"), true, true);
        StockTransaction stockTransaction = new StockTransaction(ID, stock, QUANTITY, PRICE, TRANSACTION_DATE);

        StockTransactionDTO dto = StockTransactionToDTO.convert(stockTransaction);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(ID);
        assertThat(dto.stockId()).isEqualTo(STOCK_ID);
        assertThat(dto.quantity()).isEqualTo(QUANTITY);
        assertThat(dto.price()).isEqualTo(PRICE);
        assertThat(dto.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
