package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Installment;
import gd.software.financial_manager.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface AllTransactions {

    Transaction save(Transaction transaction);

    List<Transaction> allBy(UUID id);

    void remove(UUID id);

    List<Installment> byUserIdAndType(UUID id, CategoryType type);
}
