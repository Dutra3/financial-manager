package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.ReitTransaction;

public interface AllReitTransactions {

    ReitTransaction save(ReitTransaction reitTransaction);
}
