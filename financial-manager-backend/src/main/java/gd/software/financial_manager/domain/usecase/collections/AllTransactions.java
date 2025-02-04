package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface AllTransactions {

    List<Transaction> allBy(UUID id);
}
