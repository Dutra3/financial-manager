package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.dtos.TransactionDTO;

public class TransactionToDTO {

    public static TransactionDTO convert(Transaction transaction) {
        return new TransactionDTO(transaction.id(), transaction.name(), transaction.description(), transaction.amount(),
                transaction.paymentDate(), transaction.category().id());
    }
}
