package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.infrastructure.persistence.relational.WalletRow;

public class RowToWallet {

    public static Wallet convert(WalletRow row) {
        return new Wallet(row.getId(), row.getTotalAmount(), RowToBond.convert(row.getBonds()),
                RowToStock.convert(row.getStocks()), RowToReit.convert(row.getReits()));
    }
}
