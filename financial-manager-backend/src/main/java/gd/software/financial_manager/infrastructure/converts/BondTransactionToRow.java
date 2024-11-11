package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.BondTransactionRow;

public class BondTransactionToRow {

    public static BondTransactionRow convert(BondTransaction bondTransaction) {
        return BondTransactionRow.builder()
                .id(bondTransaction.id())
                .bond(BondToRow.convert(bondTransaction.bond()))
                .quantity(bondTransaction.quantity())
                .price(bondTransaction.price())
                .transactionDate(bondTransaction.transactionDate())
                .build();
    }
}
