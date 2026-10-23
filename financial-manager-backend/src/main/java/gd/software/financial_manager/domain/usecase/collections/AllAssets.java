package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllAssets {

    Optional<Asset> by(UUID id);

    List<Asset> all();

    List<Asset> allByType(AssetType assetType);
}
