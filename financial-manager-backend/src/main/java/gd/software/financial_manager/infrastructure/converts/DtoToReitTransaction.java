package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.infrastructure.dtos.ReitTransactionDTO;

import java.util.UUID;

public class DtoToReitTransaction {

    public static ReitTransaction convert(ReitTransactionDTO reitTransactionDTO) {
        return new ReitTransaction(reitTransactionDTO.id(), getReit(reitTransactionDTO.reitId()), reitTransactionDTO.quantity(),
                reitTransactionDTO.price(), reitTransactionDTO.transactionDate());
    }

    private static Reit getReit(UUID id) {
        return new Reit(id);
    }
}
