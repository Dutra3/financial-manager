package gd.software.financial_manager.infrastructure.converts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.infrastructure.dtos.ReitTransactionDTO;
import org.junit.jupiter.api.Test;

class DtoToReitTransactionTest {

    private static final UUID ID = UUID.randomUUID();
    private static final UUID REIT_ID = UUID.randomUUID();
    private static final BigDecimal QUANTITY = new BigDecimal("10.50");
    private static final BigDecimal PRICE = new BigDecimal("150.75");
    private static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 22);

    @Test
    void should_convert_valid_dto_to_reit_transaction() {
        ReitTransactionDTO dto = new ReitTransactionDTO(ID, REIT_ID, QUANTITY, PRICE, TRANSACTION_DATE);
        ReitTransaction reitTransaction = DtoToReitTransaction.convert(dto);

        assertThat(reitTransaction).isNotNull();
        assertThat(reitTransaction.id()).isEqualTo(ID);
        assertThat(reitTransaction.reit().id()).isEqualTo(REIT_ID);
        assertThat(reitTransaction.quantity()).isEqualTo(QUANTITY);
        assertThat(reitTransaction.price()).isEqualTo(PRICE);
        assertThat(reitTransaction.transactionDate()).isEqualTo(TRANSACTION_DATE);
    }

    @Test
    void should_throw_exception_for_invalid_quantity() {
        BigDecimal invalidQuantity = BigDecimal.ZERO;

        assertThatThrownBy(() -> new ReitTransactionDTO(ID, REIT_ID, invalidQuantity, PRICE, TRANSACTION_DATE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater than zero.");
    }

    @Test
    void should_throw_exception_for_invalid_price() {
        BigDecimal invalidPrice = BigDecimal.ZERO;

        assertThatThrownBy(() -> new ReitTransactionDTO(ID, REIT_ID, QUANTITY, invalidPrice, TRANSACTION_DATE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Price must be greater than zero.");
    }
}
