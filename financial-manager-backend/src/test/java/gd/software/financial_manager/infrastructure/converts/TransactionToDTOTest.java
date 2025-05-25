package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.dtos.TransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionToDTOTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final BigDecimal AMOUNT = new BigDecimal("320.00");
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2025, 5, 20);
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void should_convert() {
        Transaction transaction = new Transaction(ID, NAME, DESCRIPTION, AMOUNT, PAYMENT_DATE, new Category(CATEGORY_ID));
        TransactionDTO dto = TransactionToDTO.convert(transaction);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(ID);
        assertThat(dto.name()).isEqualTo(NAME);
        assertThat(dto.description()).isEqualTo(DESCRIPTION);
        assertThat(dto.amount()).isEqualTo(AMOUNT);
        assertThat(dto.paymentDate()).isEqualTo(PAYMENT_DATE);
        assertThat(dto.categoryId()).isEqualTo(CATEGORY_ID);
    }
}
