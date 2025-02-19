package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.dtos.TransactionResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionToResponseTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID ID_TWO = UUID.randomUUID();
    private static final String NAME = "Transaction One";
    private static final String NAME_TWO = "Transaction Two";
    private static final String DESCRIPTION = "Description";
    private static final String DESCRIPTION_TWO = "Description Two";
    private static final BigDecimal AMOUNT = new BigDecimal("20.00");
    private static final BigDecimal AMOUNT_TWO = new BigDecimal("25.00");
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2025, 2, 20);
    private static final Category CATEGORY = new Category(UUID.randomUUID(), "Rent", CategoryType.DEBIT);
    private static final Category CATEGORY_TWO = new Category(UUID.randomUUID(), "Salary", CategoryType.CREDIT);

    @Test
    void should_convert() {
        List<Transaction> transactions = buildTransactions();
        List<TransactionResponse> responses = TransactionToResponse.convert(transactions);

        assertThat(responses).isNotEmpty().hasSize(2);
        assertThat(responses.get(0)).isNotNull();
        assertThat(responses.get(0).id()).isEqualTo(ID);
        assertThat(responses.get(0).name()).isEqualTo(NAME);
        assertThat(responses.get(0).description()).isEqualTo(DESCRIPTION);
        assertThat(responses.get(0).amount()).isEqualTo(AMOUNT);
        assertThat(responses.get(0).paymentDate()).isEqualTo(PAYMENT_DATE);
        assertThat(responses.get(0).type()).isEqualTo(CATEGORY.type().name());
        assertThat(responses.get(0).category()).isEqualTo(CATEGORY.name());
        assertThat(responses.get(1)).isNotNull();
        assertThat(responses.get(1).id()).isEqualTo(ID_TWO);
        assertThat(responses.get(1).name()).isEqualTo(NAME_TWO);
        assertThat(responses.get(1).description()).isEqualTo(DESCRIPTION_TWO);
        assertThat(responses.get(1).amount()).isEqualTo(AMOUNT_TWO);
        assertThat(responses.get(1).paymentDate()).isEqualTo(PAYMENT_DATE);
        assertThat(responses.get(1).type()).isEqualTo(CATEGORY_TWO.type().name());
        assertThat(responses.get(1).category()).isEqualTo(CATEGORY_TWO.name());
    }

    private static List<Transaction> buildTransactions() {
        Transaction transaction = new Transaction(ID, NAME, DESCRIPTION, AMOUNT, PAYMENT_DATE, CATEGORY);
        Transaction transactionTwo = new Transaction(ID_TWO, NAME_TWO, DESCRIPTION_TWO, AMOUNT_TWO, PAYMENT_DATE, CATEGORY_TWO);

        return List.of(transaction, transactionTwo);
    }
}
