package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.persistence.relational.TransactionRow;

public class RowToTransaction {

    public static Transaction convert(TransactionRow row) {
        return new Transaction(row.getId(), row.getName(), row.getDescription(), row.getAmount(), row.getPaymentDate(),
                RowToCategory.convert(row.getCategory()));
    }
}
