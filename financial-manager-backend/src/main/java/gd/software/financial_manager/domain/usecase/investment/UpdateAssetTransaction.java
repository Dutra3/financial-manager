package gd.software.financial_manager.domain.usecase.investment;

import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.domain.usecase.collections.AllAssetTransactions;
import gd.software.financial_manager.infrastructure.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class UpdateAssetTransaction {

    private static final Logger logger = LoggerFactory.getLogger(UpdateAssetTransaction.class);

    private final AllAssetTransactions allAssetTransactions;

    public UpdateAssetTransaction(AllAssetTransactions allAssetTransactions) {
        this.allAssetTransactions = allAssetTransactions;
    }

    public AssetTransaction use(UUID id, AssetTransactionType type, BigDecimal quantity, BigDecimal price, LocalDate date) {
        AssetTransaction existing = allAssetTransactions.byId(id)
                .orElseThrow(() -> new EntityNotFoundException("Asset transaction not found with id " + id));

        SecurityUtils.requireOwner(existing.userId());

        AssetTransaction updated = new AssetTransaction(id, existing.asset(), existing.userId(),
                type, quantity, price, date);

        logger.info("Updating asset transaction {} for user {}.", id, existing.userId());
        return allAssetTransactions.save(updated);
    }
}
