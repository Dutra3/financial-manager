package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitTransactionRow;

public class ReitTransactionToRow {

    public static ReitTransactionRow convert(ReitTransaction reitTransaction) {
        return ReitTransactionRow.builder()
                .id(reitTransaction.id())
                .reit(ReitToRow.convert(reitTransaction.reit()))
                .quantity(reitTransaction.quantity())
                .price(reitTransaction.price())
                .transactionDate(reitTransaction.transactionDate())
                .build();
    }
}
