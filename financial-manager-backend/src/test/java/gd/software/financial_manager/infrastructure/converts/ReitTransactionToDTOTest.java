package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.infrastructure.dtos.ReitTransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ReitTransactionToDTOTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID REIT_ID = UUID.randomUUID();
    private static final BigDecimal QUANTITY = new BigDecimal("10.50");
    private static final BigDecimal PRICE = new BigDecimal("150.75");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert_reit_transaction_to_dto() {
        Reit reit = new Reit(REIT_ID);
        ReitTransaction reitTransaction = new ReitTransaction(ID, reit, QUANTITY, PRICE, TRANSACTION_DATE);
        ReitTransactionDTO dto = ReitTransactionToDTO.convert(reitTransaction);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(ID);
        assertThat(dto.reitId()).isEqualTo(REIT_ID);
        assertThat(dto.quantity()).isEqualTo(QUANTITY);
        assertThat(dto.price()).isEqualTo(PRICE);
        assertThat(dto.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }
}
