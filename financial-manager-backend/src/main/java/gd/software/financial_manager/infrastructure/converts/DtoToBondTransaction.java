package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.infrastructure.dtos.BondTransactionDTO;

import java.util.UUID;

public class DtoToBondTransaction {

    public static BondTransaction convert(BondTransactionDTO bondTransactionDTO) {
        return new BondTransaction(bondTransactionDTO.id(), getBond(bondTransactionDTO.bondId()), bondTransactionDTO.quantity(),
                bondTransactionDTO.price(), bondTransactionDTO.transactionDate());
    }

    private static Bond getBond(UUID id) {
        return new Bond(id);
    }
}
