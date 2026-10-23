package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.infrastructure.dtos.AssetResponse;

import java.util.List;

public class AssetToResponse {

    public static AssetResponse convert(Asset asset) {
        return new AssetResponse(asset.id(), asset.ticker(), asset.name(),
                asset.assetType() != null ? asset.assetType().name() : null, asset.description(),
                asset.type(), asset.industrySegment(), asset.price(), asset.tagAlong(), asset.peRatio(),
                asset.dividendYield(), asset.pbRatio(), asset.lastDividend(), asset.isBesst(), asset.isNewMarket());
    }

    public static List<AssetResponse> convert(List<Asset> assets) {
        return assets.stream().map(AssetToResponse::convert).toList();
    }
}
