package gd.software.financial_manager.domain.usecase.wallet;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.domain.usecase.collections.AllWallets;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FetchWallet {

    private static final Logger logger = LoggerFactory.getLogger(FetchWallet.class);

    private final AllWallets allWallets;

    public FetchWallet(AllWallets allWallets) {
        this.allWallets = allWallets;
    }

    public Wallet use(UUID id) {
        return allWallets.by(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
