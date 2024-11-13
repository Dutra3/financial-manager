package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfTransactionRow;

public class RowToEtfTransaction {

    public static EtfTransaction convert(EtfTransactionRow etfTransactionRow) {
        return new EtfTransaction(etfTransactionRow.getId(), RowToEtf.convert(etfTransactionRow.getEtf()),
                etfTransactionRow.getQuantity(), etfTransactionRow.getPrice(), etfTransactionRow.getTransactionDate());
    }
}
