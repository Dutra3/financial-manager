package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.infrastructure.dtos.EtfTransactionDTO;

import java.util.UUID;

public class DtoToEtfTransaction {

    public static EtfTransaction convert(EtfTransactionDTO etfTransactionDTO) {
        return new EtfTransaction(etfTransactionDTO.id(), getEtf(etfTransactionDTO.etfId()), etfTransactionDTO.quantity(),
                etfTransactionDTO.price(), etfTransactionDTO.transactionDate());
    }

    private static Etf getEtf(UUID id) {
        return new Etf(id);
    }
}
