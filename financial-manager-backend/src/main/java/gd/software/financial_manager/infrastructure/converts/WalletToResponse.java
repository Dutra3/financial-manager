package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.infrastructure.dtos.WalletResponse;

public class WalletToResponse {

    public static WalletResponse convert(Wallet wallet) {
        return new WalletResponse(wallet.id(), wallet.totalAmount(), BondToResponse.convert(wallet.bonds()),
                StockToResponse.convert(wallet.stocks()), ReitToResponse.convert(wallet.reits()));
    }
}
