package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.dtos.TransactionDTO;

public class DtoToTransaction {

    public static Transaction convert(TransactionDTO transactionDTO) {
        return new Transaction(transactionDTO.id(), transactionDTO.name(), transactionDTO.description(), transactionDTO.amount(),
                transactionDTO.paymentDate(), new Category(transactionDTO.categoryId()));
    }
}
