package gd.software.financial_manager.domain.usecase.bond;

import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllBondTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateBondTransaction {

    private static final Logger logger = LoggerFactory.getLogger(CreateBondTransaction.class);

    private final AllBondTransactions allBondTransactions;

    public CreateBondTransaction(AllBondTransactions allBondTransactions) {
        this.allBondTransactions = allBondTransactions;
    }

    public BondTransaction use(BondTransaction bondTransaction) {
        logger.info("Creating bond transaction for {}.", bondTransaction.bond().name());
        return allBondTransactions.save(bondTransaction);
    }
}
