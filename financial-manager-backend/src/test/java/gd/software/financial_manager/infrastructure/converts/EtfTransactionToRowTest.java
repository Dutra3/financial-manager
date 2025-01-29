package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfTransactionRow;
import org.junit.jupiter.api.Test;

class EtfTransactionToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID ETF_ID = UUID.randomUUID();
    private static final Etf ETF = new Etf(ETF_ID);
    private static final BigDecimal QUANTITY = new BigDecimal("21.00");
    private static final BigDecimal PRICE = new BigDecimal("89.32");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert() {
        EtfTransaction etfTransaction = new EtfTransaction(ID, ETF, QUANTITY, PRICE, TRANSACTION_DATE);
        EtfTransactionRow row = EtfTransactionToRow.convert(etfTransaction);

        assertThat(row).isNotNull();
        assertThat(row.getId()).isEqualTo(ID);
        assertThat(row.getEtf().getId()).isEqualTo(ETF.id());
        assertThat(row.getQuantity()).isEqualTo(QUANTITY);
        assertThat(row.getPrice()).isEqualTo(PRICE);
        assertThat(row.getTransactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
