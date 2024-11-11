package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.infrastructure.dtos.BondTransactionDTO;

public class BondTransactionToDTO {

    public static BondTransactionDTO convert(BondTransaction bondTransaction) {
        return new BondTransactionDTO(bondTransaction.id(), bondTransaction.bond().id(), bondTransaction.quantity(),
                bondTransaction.price(), bondTransaction.transactionDate());
    }
}
