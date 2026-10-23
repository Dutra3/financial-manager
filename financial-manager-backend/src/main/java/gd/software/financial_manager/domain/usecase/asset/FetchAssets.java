package gd.software.financial_manager.domain.usecase.asset;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetType;
import gd.software.financial_manager.domain.usecase.collections.AllAssets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FetchAssets {

    private static final Logger logger = LoggerFactory.getLogger(FetchAssets.class);

    private final AllAssets allAssets;

    public FetchAssets(AllAssets allAssets) {
        this.allAssets = allAssets;
    }

    public Asset by(UUID id) throws Exception {
        logger.info("Find asset with id {}.", id);
        return allAssets.by(id).orElseThrow(() -> new Exception("cant_find_asset_with_id"));
    }

    public List<Asset> all() {
        logger.info("Fetch all assets.");
        return allAssets.all();
    }

    public List<Asset> allByType(AssetType assetType) {
        logger.info("Fetch all assets with type {}.", assetType);
        return allAssets.allByType(assetType);
    }
}
