package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitTransactionRow;

public class RowToReitTransaction {

    public static ReitTransaction convert(ReitTransactionRow reitTransactionRow) {
        return new ReitTransaction(reitTransactionRow.getId(), RowToReit.convert(reitTransactionRow.getReit()),
                reitTransactionRow.getQuantity(), reitTransactionRow.getPrice(), reitTransactionRow.getTransactionDate());
    }
}
