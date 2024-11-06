package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.infrastructure.dtos.ReitTransactionDTO;

public class ReitTransactionToDTO {

    public static ReitTransactionDTO convert(ReitTransaction reitTransaction) {
        return new ReitTransactionDTO(reitTransaction.id(), reitTransaction.reit().id(), reitTransaction.quantity(),
                reitTransaction.price(), reitTransaction.transactionDate());
    }
}
