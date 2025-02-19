package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryRow;
import gd.software.financial_manager.infrastructure.persistence.relational.CategoryTypeRow;
import gd.software.financial_manager.infrastructure.persistence.relational.TransactionRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RowToTransactionTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Transaction One";
    private static final String DESCRIPTION = "Description";
    private static final BigDecimal AMOUNT = new BigDecimal("20.00");
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2025, 2, 20);
    private static final UUID CATEGORY_ID = UUID.randomUUID();
    private static final CategoryRow CATEGORY = CategoryRow.builder().id(CATEGORY_ID).name("Rent").type(CategoryTypeRow.DEBIT).build();

    @Test
    void should_convert() {
        TransactionRow row = TransactionRow.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .amount(AMOUNT)
                .paymentDate(PAYMENT_DATE)
                .category(CATEGORY)
                .build();
        Transaction transaction = RowToTransaction.convert(row);

        assertThat(transaction).isNotNull();
        assertThat(transaction.id()).isEqualTo(ID);
        assertThat(transaction.name()).isEqualTo(NAME);
        assertThat(transaction.description()).isEqualTo(DESCRIPTION);
        assertThat(transaction.amount()).isEqualTo(AMOUNT);
        assertThat(transaction.paymentDate()).isEqualTo(PAYMENT_DATE);
        assertThat(transaction.category().id()).isEqualTo(CATEGORY.getId());
    }
}
