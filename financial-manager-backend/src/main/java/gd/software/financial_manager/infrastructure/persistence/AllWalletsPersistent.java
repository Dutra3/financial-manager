package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.domain.usecase.collections.AllWallets;
import gd.software.financial_manager.infrastructure.persistence.repository.WalletRepository;

import java.util.Optional;
import java.util.UUID;

public class AllWalletsPersistent implements AllWallets {

    private final WalletRepository repository;

    public AllWalletsPersistent(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Wallet> by(UUID id) {
        return repository.findById(id).map(RowToWallet::convert);
    }
}
