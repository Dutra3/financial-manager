package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.infrastructure.dtos.EtfTransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DtoToEtfTransactionTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID ETF_ID = UUID.randomUUID();
    private static final BigDecimal QUANTITY = new BigDecimal("21.00");
    private static final BigDecimal PRICE = new BigDecimal("89.32");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert() {
        EtfTransactionDTO dto = new EtfTransactionDTO(ID, ETF_ID, QUANTITY, PRICE, TRANSACTION_DATE);
        EtfTransaction etfTransaction = DtoToEtfTransaction.convert(dto);

        assertThat(etfTransaction).isNotNull();
        assertThat(etfTransaction.id()).isEqualTo(ID);
        assertThat(etfTransaction.etf().id()).isEqualTo(ETF_ID);
        assertThat(etfTransaction.quantity()).isEqualTo(QUANTITY);
        assertThat(etfTransaction.price()).isEqualTo(PRICE);
        assertThat(etfTransaction.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
