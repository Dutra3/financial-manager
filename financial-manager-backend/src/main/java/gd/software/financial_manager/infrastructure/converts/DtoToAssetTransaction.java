package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.infrastructure.dtos.AssetTransactionDTO;

public class DtoToAssetTransaction {

    public static AssetTransaction convert(AssetTransactionDTO dto) {
        return new AssetTransaction(dto.id(), new Asset(dto.assetId()), dto.userId(),
                AssetTransactionType.valueOf(dto.type()), dto.quantity(), dto.price(), dto.transactionDate());
    }
}
