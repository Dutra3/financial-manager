package gd.software.financial_manager.domain.usecase.reit;

import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllReitTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateReitTransaction {

    private static final Logger logger = LoggerFactory.getLogger(CreateReitTransaction.class);

    private final AllReitTransactions allReitTransactions;

    public CreateReitTransaction(AllReitTransactions allReitTransactions) {
        this.allReitTransactions = allReitTransactions;
    }

    public ReitTransaction use(ReitTransaction reitTransaction) {
        logger.info("Creating reit transaction for {}.", reitTransaction.reit().name());
        return allReitTransactions.save(reitTransaction);
    }
}
