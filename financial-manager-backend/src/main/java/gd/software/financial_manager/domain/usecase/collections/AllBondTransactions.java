package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.BondTransaction;

public interface AllBondTransactions {

    BondTransaction save(BondTransaction bondTransaction);
}
