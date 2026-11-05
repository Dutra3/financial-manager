package gd.software.financial_manager.domain.usecase.investment;

import gd.software.financial_manager.domain.usecase.collections.AllAssetTransactions;
import gd.software.financial_manager.infrastructure.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteAssetTransaction {

    private static final Logger logger = LoggerFactory.getLogger(DeleteAssetTransaction.class);

    private final AllAssetTransactions allAssetTransactions;

    public DeleteAssetTransaction(AllAssetTransactions allAssetTransactions) {
        this.allAssetTransactions = allAssetTransactions;
    }

    public void use(UUID id) {
        logger.info("Deleting asset transaction with id {}.", id);
        allAssetTransactions.byId(id).ifPresent(t -> SecurityUtils.requireOwner(t.userId()));
        allAssetTransactions.remove(id);
    }
}
