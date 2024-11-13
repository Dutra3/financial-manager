package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllBondTransactions;
import gd.software.financial_manager.infrastructure.converts.BondTransactionToRow;
import gd.software.financial_manager.infrastructure.converts.RowToBondTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.BondTransactionRow;
import gd.software.financial_manager.infrastructure.persistence.repository.BondTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllBondTransactionsPersistent implements AllBondTransactions {

    private static final Logger logger = LoggerFactory.getLogger(AllBondTransactionsPersistent.class);

    @Autowired
    private BondTransactionRepository repository;

    @Override
    public BondTransaction save(BondTransaction bondTransaction) {
        BondTransactionRow bondTransactionRow = BondTransactionToRow.convert(bondTransaction);

        BondTransactionRow savedBondTransactionRow = repository.save(bondTransactionRow);
        logger.info("Saved bond transaction {}.", savedBondTransactionRow);

        return RowToBondTransaction.convert(savedBondTransactionRow);
    }
}
