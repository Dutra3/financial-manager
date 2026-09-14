package gd.software.financial_manager.domain.usecase.stock;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllStockTransactions;
import gd.software.financial_manager.domain.usecase.collections.AllStocks;
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
class StockUseCasesTest {

    @Mock
    private AllStockTransactions allStockTransactions;

    @Mock
    private AllStocks allStocks;

    @InjectMocks
    private CreateStockTransaction createStockTransaction;

    @InjectMocks
    private FetchStock fetchStock;

    private static final UUID STOCK_ID = UUID.randomUUID();
    private static final UUID TRANSACTION_ID = UUID.randomUUID();

    @Test
    void createStockTransaction_should_delegate_to_allStockTransactions_save() {
        Stock stock = new Stock(STOCK_ID, "Petrobras", "PETR4", "Oil company", "Stock", "Energy",
                new BigDecimal("0.80"), new BigDecimal("35.50"), true, true);
        StockTransaction input = new StockTransaction(null, stock, new BigDecimal("100"),
                new BigDecimal("35.50"), LocalDate.of(2025, 1, 20));
        StockTransaction saved = new StockTransaction(TRANSACTION_ID, stock, new BigDecimal("100"),
                new BigDecimal("35.50"), LocalDate.of(2025, 1, 20));
        when(allStockTransactions.save(input)).thenReturn(saved);

        StockTransaction result = createStockTransaction.use(input);

        assertThat(result).isEqualTo(saved);
        verify(allStockTransactions).save(input);
    }

    @Test
    void fetchStock_by_should_return_stock_when_found() throws Exception {
        Stock stock = new Stock(STOCK_ID, "Petrobras", "PETR4", "Oil company", "Stock", "Energy",
                new BigDecimal("0.80"), new BigDecimal("35.50"), true, true);
        when(allStocks.by(STOCK_ID)).thenReturn(Optional.of(stock));

        Stock result = fetchStock.by(STOCK_ID);

        assertThat(result).isEqualTo(stock);
        verify(allStocks).by(STOCK_ID);
    }

    @Test
    void fetchStock_by_should_throw_when_not_found() {
        when(allStocks.by(STOCK_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fetchStock.by(STOCK_ID))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("cant_find_stock_with_id");
    }

    @Test
    void fetchStock_all_should_delegate_to_allStocks_all() {
        List<Stock> stocks = List.of(
                new Stock(STOCK_ID, "Petrobras", "PETR4", "Oil company", "Stock", "Energy",
                        new BigDecimal("0.80"), new BigDecimal("35.50"), true, true));
        when(allStocks.all()).thenReturn(stocks);

        List<Stock> result = fetchStock.all();

        assertThat(result).isEqualTo(stocks);
        verify(allStocks).all();
    }
}
