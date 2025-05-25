package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.persistence.relational.TransactionRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final BigDecimal AMOUNT = new BigDecimal("320.00");
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2025, 5, 20);
    private static final UUID CATEGORY_ID = UUID.randomUUID();
    private static final Category CATEGORY = new Category(CATEGORY_ID, "Rent", CategoryType.DEBIT);

    @Test
    void should_convert() {
        Transaction transaction = new Transaction(ID, NAME, DESCRIPTION, AMOUNT, PAYMENT_DATE, CATEGORY);
        TransactionRow row = TransactionToRow.convert(transaction);

        assertThat(row).isNotNull();
        assertThat(row.getId()).isEqualTo(ID);
        assertThat(row.getName()).isEqualTo(NAME);
        assertThat(row.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(row.getAmount()).isEqualTo(AMOUNT);
        assertThat(row.getPaymentDate()).isEqualTo(PAYMENT_DATE);
        assertThat(row.getCategory().getId()).isEqualTo(CATEGORY_ID);
        assertThat(row.getUser()).isNull();
    }
}
