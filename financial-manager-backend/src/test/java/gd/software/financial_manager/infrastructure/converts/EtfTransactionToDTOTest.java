package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.infrastructure.dtos.EtfTransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EtfTransactionToDTOTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID ETF_ID = UUID.randomUUID();
    private static final Etf ETF = new Etf(ETF_ID);
    private static final BigDecimal QUANTITY = new BigDecimal("21.00");
    private static final BigDecimal PRICE = new BigDecimal("89.32");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert() {
        EtfTransaction etfTransaction = new EtfTransaction(ID, ETF, QUANTITY, PRICE, TRANSACTION_DATE);
        EtfTransactionDTO dto = EtfTransactionToDTO.convert(etfTransaction);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(ID);
        assertThat(dto.etfId()).isEqualTo(ETF_ID);
        assertThat(dto.quantity()).isEqualTo(QUANTITY);
        assertThat(dto.price()).isEqualTo(PRICE);
        assertThat(dto.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
