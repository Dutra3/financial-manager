package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.BondTransactionRow;

public class RowToBondTransaction {

    public static BondTransaction convert(BondTransactionRow bondTransactionRow) {
        return new BondTransaction(bondTransactionRow.getId(), RowToBond.convert(bondTransactionRow.getBond()),
                bondTransactionRow.getQuantity(), bondTransactionRow.getPrice(), bondTransactionRow.getTransactionDate());
    }
}
