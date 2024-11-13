package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.EtfTransaction;

public interface AllEtfTransactions {

    EtfTransaction save(EtfTransaction etfTransaction);
}
