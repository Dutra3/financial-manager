package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.dtos.TransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DtoToTransactionTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final BigDecimal AMOUNT = new BigDecimal("320.00");
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2025, 5, 20);
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void should_convert() {
        TransactionDTO dto = new TransactionDTO(ID, NAME, DESCRIPTION, AMOUNT, PAYMENT_DATE, CATEGORY_ID);
        Transaction transaction = DtoToTransaction.convert(dto);

        assertThat(transaction).isNotNull();
        assertThat(transaction.id()).isEqualTo(ID);
        assertThat(transaction.name()).isEqualTo(NAME);
        assertThat(transaction.description()).isEqualTo(DESCRIPTION);
        assertThat(transaction.amount()).isEqualTo(AMOUNT);
        assertThat(transaction.paymentDate()).isEqualTo(PAYMENT_DATE);
        assertThat(transaction.category().id()).isEqualTo(CATEGORY_ID);
    }
}
