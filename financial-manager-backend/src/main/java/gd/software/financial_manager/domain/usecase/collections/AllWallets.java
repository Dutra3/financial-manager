package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface AllWallets {

    Optional<Wallet> by(UUID id);
}
