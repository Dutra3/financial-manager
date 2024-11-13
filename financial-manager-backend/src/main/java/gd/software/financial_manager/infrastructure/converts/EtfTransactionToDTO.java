package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.infrastructure.dtos.EtfTransactionDTO;

public class EtfTransactionToDTO {

    public static EtfTransactionDTO convert(EtfTransaction etfTransaction) {
        return new EtfTransactionDTO(etfTransaction.id(), etfTransaction.etf().id(), etfTransaction.quantity(),
                etfTransaction.price(), etfTransaction.transactionDate());
    }
}
