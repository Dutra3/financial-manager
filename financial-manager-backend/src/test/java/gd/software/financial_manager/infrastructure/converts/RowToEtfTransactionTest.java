package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfRow;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfTransactionRow;
import org.junit.jupiter.api.Test;

class RowToEtfTransactionTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID ETF_ID = UUID.randomUUID();
    private static final EtfRow ETF = EtfRow.builder().id(ETF_ID).build();
    private static final BigDecimal QUANTITY = new BigDecimal("21.00");
    private static final BigDecimal PRICE = new BigDecimal("89.32");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert() {
        EtfTransactionRow etfTransactionRow = EtfTransactionRow.builder()
                .id(ID)
                .etf(ETF)
                .quantity(QUANTITY)
                .price(PRICE)
                .transactionDate(TRANSACTION_DATE)
                .build();
        EtfTransaction etfTransaction = RowToEtfTransaction.convert(etfTransactionRow);

        assertThat(etfTransaction).isNotNull();
        assertThat(etfTransaction.id()).isEqualTo(ID);
        assertThat(etfTransaction.etf().id()).isEqualTo(ETF_ID);
        assertThat(etfTransaction.quantity()).isEqualTo(QUANTITY);
        assertThat(etfTransaction.price()).isEqualTo(PRICE);
        assertThat(etfTransaction.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
