package gd.software.financial_manager.domain.usecase.etf;

import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllEtfTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateEtfTransaction {

    private static final Logger logger = LoggerFactory.getLogger(CreateEtfTransaction.class);

    private final AllEtfTransactions allEtfTransactions;

    public CreateEtfTransaction(AllEtfTransactions allEtfTransactions) {
        this.allEtfTransactions = allEtfTransactions;
    }

    public EtfTransaction use(EtfTransaction etfTransaction) {
        logger.info("Creating etf transaction for {}.", etfTransaction.etf().name());
        return allEtfTransactions.save(etfTransaction);
    }
}
