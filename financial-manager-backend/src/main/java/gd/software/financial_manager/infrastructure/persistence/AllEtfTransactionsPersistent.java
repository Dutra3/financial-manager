package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllEtfTransactions;
import gd.software.financial_manager.infrastructure.converts.EtfTransactionToRow;
import gd.software.financial_manager.infrastructure.converts.RowToEtfTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfTransactionRow;
import gd.software.financial_manager.infrastructure.persistence.repository.EtfTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AllEtfTransactionsPersistent implements AllEtfTransactions {

    private static final Logger logger = LoggerFactory.getLogger(AllEtfTransactionsPersistent.class);

    @Autowired
    private EtfTransactionRepository repository;

    @Override
    public EtfTransaction save(EtfTransaction etfTransaction) {
        EtfTransactionRow etfTransactionRow = EtfTransactionToRow.convert(etfTransaction);

        EtfTransactionRow savedEtfTransactionRow = repository.save(etfTransactionRow);
        logger.info("Saved etf transaction {}.", savedEtfTransactionRow);

        return RowToEtfTransaction.convert(savedEtfTransactionRow);
    }
}
