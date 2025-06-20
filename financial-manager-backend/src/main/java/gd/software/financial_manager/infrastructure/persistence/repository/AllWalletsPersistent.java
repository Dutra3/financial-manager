package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.domain.usecase.collections.AllWallets;

import java.util.Optional;
import java.util.UUID;

public class AllWalletsPersistent implements AllWallets {

    @Override
    public Optional<Wallet> by(UUID id) {
        return Optional.empty();
    }
}
