package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfTransactionRow;

public class EtfTransactionToRow {

    public static EtfTransactionRow convert(EtfTransaction etfTransaction) {
        return EtfTransactionRow.builder()
                .id(etfTransaction.id())
                .etf(EtfToRow.convert(etfTransaction.etf()))
                .quantity(etfTransaction.quantity())
                .price(etfTransaction.price())
                .transactionDate(etfTransaction.transactionDate())
                .build();
    }
}
