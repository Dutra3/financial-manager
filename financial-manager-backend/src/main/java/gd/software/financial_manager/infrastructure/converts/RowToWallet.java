package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Wallet;

public class RowToWallet {

    public static Wallet convert(WalletRow row) {
        return new Wallet(row.id(), row.name());
    }
}
