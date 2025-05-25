package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.persistence.relational.TransactionRow;

public class TransactionToRow {

    public static TransactionRow convert(Transaction transaction) {
        return TransactionRow.builder()
                .id(transaction.id())
                .name(transaction.name())
                .description(transaction.description())
                .amount(transaction.amount())
                .paymentDate(transaction.paymentDate())
                .category(CategoryToRow.convert(transaction.category()))
                .build();
    }
}
